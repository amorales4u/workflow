package dev.c20.workflow.storage.repositories;

import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Data;
import dev.c20.workflow.storage.entities.adds.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> {

    @Query( "select o from Data o where o.parent = ?1 ")
    public Data getByParent(Long parent);

    @Modifying
    @Query( "insert into Data ( parent, data ) select ?1, data from Data o where o.parent = ?2")
    @Transactional
    public int copyTo(Long target, Long source);



}
