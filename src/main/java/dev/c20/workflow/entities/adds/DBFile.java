package dev.c20.workflow.entities.adds;


import dev.c20.workflow.WorkflowApplication;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name= WorkflowApplication.DB_PREFIX + "STG_FILE")
public class DBFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=WorkflowApplication.DB_PREFIX + "ID")
    private Long id;

    @Lob
    @Column(name=WorkflowApplication.DB_PREFIX + "FILE",columnDefinition="BLOB")
    private byte[] file;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBFile dbFile = (DBFile) o;
        return id.equals(dbFile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public DBFile setId(Long id) {
        this.id = id;
        return this;
    }

    public byte[] getFile() {
        return file;
    }

    public DBFile setFile(byte[] file) {
        this.file = file;
        return this;
    }
}
