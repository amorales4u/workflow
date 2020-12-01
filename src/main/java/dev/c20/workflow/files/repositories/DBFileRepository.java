package dev.c20.workflow.files.repositories;

import dev.c20.workflow.files.entities.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, Long> {



}
