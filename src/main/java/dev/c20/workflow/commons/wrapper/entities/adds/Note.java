package dev.c20.workflow.commons.wrapper.entities.adds;

import dev.c20.workflow.commons.wrapper.entities.Storage;

import java.util.Date;

public class Note {

    private Long id;

    private Storage parent;

    private String creator;

    private Date created;

    private String image;

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
