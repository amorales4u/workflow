package dev.c20.workflow.storage.repositories;

import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Log;
import dev.c20.workflow.storage.entities.adds.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValueRepository extends JpaRepository<Value, Long> {

    @Query( "select o from Value o where o.parent = ?1 order by o.id")
    public List<Value> getAll(Storage parent);

    @Query( "select o from Value o where o.parent = ?1 and o.name = ?2 order by o.id")
    public Value getByName(Storage parent, String name);


}
