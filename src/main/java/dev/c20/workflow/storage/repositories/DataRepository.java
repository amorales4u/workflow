package dev.c20.workflow.storage.repositories;

import dev.c20.workflow.storage.entities.adds.Data;
import dev.c20.workflow.storage.entities.adds.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> {

    @Query( "select o from Data o where o.parent = ?1 ")
    public Data getByParent(Long parent);


}
