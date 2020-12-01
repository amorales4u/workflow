package dev.c20.workflow.storage.repositories;

import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query( "select o from Note o where o.parent = ?1 order by o.id")
    public List<Note> getAll(Storage parent);


}
