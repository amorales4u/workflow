package dev.c20.workflow.storage.repositories;

import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Attach;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface AttachRepository extends JpaRepository<Attach, Long> {

    @Query( "select o from Attach o where o.parent = ?1 order by o.id")
    public List<Attach> getAll(Storage parent);

    @Query( "select o from Attach o where o.parent = ?1 and o.name = ?2 order by o.id")
    public Attach getByName(Storage parent, String name);

    @Modifying
    @Query( "insert into Attach ( parent, modified, modifier, name, file  ) select ?1, modified, modifier, name, file  from Attach o where o.parent = ?2")
    @Transactional
    public int copyTo(Storage targetStorage, Storage fromStorage);


}
