package dev.c20.workflow.entities.adds;


import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.entities.Storage;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name= WorkflowApplication.DB_PREFIX + "STG_NOTE")
class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=WorkflowApplication.DB_PREFIX + "ID")
    private Long id;

    @ManyToOne( fetch=FetchType.LAZY)
    @JoinColumn( name=WorkflowApplication.DB_PREFIX + "STORAGE")
    private Storage parent;

    @Column( name=WorkflowApplication.DB_PREFIX + "CREATOR")
    private String creator;

    @Column(name=WorkflowApplication.DB_PREFIX + "CREATED")
    private Date created;

    @Column(name=WorkflowApplication.DB_PREFIX + "IMAGE")
    private String image;

    @Column(name=WorkflowApplication.DB_PREFIX + "COMMENT", length=2000)
    private String comment;


    public boolean equals(Object o) {
        if (!(o instanceof Note)) return false;

        Note obj = (Note) o;

        if (id != obj.id) return false;

        return true;
    }

    public int hashCode() {

        return (id != null ? id.hashCode() : 0);
    }

    public Long getId() {
        return id;
    }

    public Note setId(Long id) {
        this.id = id;
        return this;
    }

    public Storage getParent() {
        return parent;
    }

    public Note setParent(Storage parent) {
        this.parent = parent;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public Note setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Date getCreated() {
        return created;
    }

    public Note setCreated(Date created) {
        this.created = created;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Note setImage(String image) {
        this.image = image;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Note setComment(String comment) {
        this.comment = comment;
        return this;
    }
}
