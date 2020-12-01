package dev.c20.workflow.storage.repositories;

import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Perm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermRepository extends JpaRepository<Perm, Long> {

    @Query( "select o from Perm o where o.parent = ?1 order by o.user")
    public List<Perm> getAll(Storage parent);

    @Query( "select o from Perm o where o.parent = ?1 and o.user = ?2 ")
    public Perm getByUser(Storage parent, String user);


}
