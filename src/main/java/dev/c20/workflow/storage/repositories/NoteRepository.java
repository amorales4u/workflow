package dev.c20.workflow.storage.repositories;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query( "select o from Note o where o.parent = ?1 order by o.id")
    public List<Note> getAll(Storage parent);

    @Modifying
    @Query( "insert into Note ( parent, creator, created, image, comment ) select ?1, creator, created, image, comment from Note o where o.parent = ?2")
    @Transactional
    public int copyTo(Storage target, Storage source);


}
