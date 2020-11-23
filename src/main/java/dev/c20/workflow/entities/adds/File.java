package dev.c20.workflow.entities.adds;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.entities.Storage;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name=WorkflowApplication.DB_PREFIX + "STG_FILE")
class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=WorkflowApplication.DB_PREFIX + "ID")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn( name= WorkflowApplication.DB_PREFIX + "STORAGE")
    private Storage parent;

    @Column(name=WorkflowApplication.DB_PREFIX + "MODIFIED")
    private Date modified;

    @Column(name=WorkflowApplication.DB_PREFIX + "MODIFIER", length=20)
    private String modifier;

    @Column(name=WorkflowApplication.DB_PREFIX + "COMMENT", length=2000)
    private String comment;

    @Column(name=WorkflowApplication.DB_PREFIX + "FILE")
    private Long file;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File log = (File) o;
        return id.equals(log.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public File setId(Long id) {
        this.id = id;
        return this;
    }

    public Storage getParent() {
        return parent;
    }

    public File setParent(Storage parent) {
        this.parent = parent;
        return this;
    }

    public Date getModified() {
        return modified;
    }

    public File setModified(Date modified) {
        this.modified = modified;
        return this;
    }

    public String getModifier() {
        return modifier;
    }

    public File setModifier(String modifier) {
        this.modifier = modifier;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public File setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Long getFile() {
        return file;
    }

    public File setFile(Long file) {
        this.file = file;
        return this;
    }

}
