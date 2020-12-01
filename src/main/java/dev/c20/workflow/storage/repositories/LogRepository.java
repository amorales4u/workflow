package dev.c20.workflow.storage.repositories;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Log;
import dev.c20.workflow.storage.entities.adds.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    @Query( "select o from Log o where o.parent = ?1 order by o.id")
    public List<Log> getAll(Storage parent);

    @Modifying
    @Query( "insert into Log ( parent, modified, modifier, comment, commentId, type ) select ?1, modified, modifier, comment, commentId, type from Log o where o.parent = ?2")
    @Transactional
    public int copyTo(Storage target, Storage source);

}
