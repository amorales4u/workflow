package dev.c20.workflow.storage.repositories;

import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.ProtectedValue;
import dev.c20.workflow.storage.entities.adds.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtectedValueRepository extends JpaRepository<ProtectedValue, Long> {

    public static final int PROPERTIES = 1000;

    @Query( "select o from ProtectedValue o where o.parent = ?1 order by o.order")
    List<ProtectedValue> getAll(Storage parent);

    @Query( "select o from ProtectedValue o where o.parent = ?1 and o.intValue = 1000 order by o.order")
    List<ProtectedValue> getAllProperties(Storage parent);

    @Query( "select o from Value o where o.parent = ?1 and o.name = ?2 order by o.order")
    Value getByName(Storage parent, String name);

    @Query( "select o from ProtectedValue o where o.name = ?1 and o.value = ?2 order by o.order")
    List<Value> getFindByNameValue(String name, String value);

}
