package dev.c20.workflow.repositories;

import dev.c20.workflow.entities.Storage;
import dev.c20.workflow.entities.adds.Attach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachRepository extends JpaRepository<Attach, Long> {

    @Query( "select o from Attach o where o.parent = ?1 order by o.id")
    public List<Attach> getAll(Storage parent);

    @Query( "select o from Attach o where o.parent = ?1 and o.name = ?2 order by o.id")
    public Attach getByName(Storage parent, String name);


}
