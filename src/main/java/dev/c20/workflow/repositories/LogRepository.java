package dev.c20.workflow.repositories;

import dev.c20.workflow.entities.Storage;
import dev.c20.workflow.entities.adds.Log;
import dev.c20.workflow.entities.adds.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    @Query( "select o from Log o where o.parent = ?1 order by o.id")
    public List<Log> getAll(Storage parent);


}
