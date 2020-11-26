package dev.c20.workflow.repositories;

import dev.c20.workflow.entities.Storage;
import dev.c20.workflow.entities.adds.Data;
import dev.c20.workflow.entities.adds.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> {

    @Query( "select o from Value o where o.parent = ?1 ")
    public Data getByParent(Storage parent);


}
