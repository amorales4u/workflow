package dev.c20.workflow.commons.wrapper.entities.adds;

import dev.c20.workflow.commons.wrapper.entities.Storage;

import java.util.Date;
import java.util.Objects;

public class Attach {

    private Long id;

    private Storage parent;

    private Date modified;

    private String modifier;

    private String name;

    private Long file;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attach log = (Attach) o;
        return id.equals(log.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public Attach setId(Long id) {
        this.id = id;
        return this;
    }

    public Storage getParent() {
        return parent;
    }

    public Attach setParent(Storage parent) {
        this.parent = parent;
        return this;
    }

    public Date getModified() {
        return modified;
    }

    public Attach setModified(Date modified) {
        this.modified = modified;
        return this;
    }

    public String getModifier() {
        return modifier;
    }

    public Attach setModifier(String modifier) {
        this.modifier = modifier;
        return this;
    }

    public String getName() {
        return name;
    }

    public Attach setName(String comment) {
        this.name = comment;
        return this;
    }

    public Long getFile() {
        return file;
    }

    public Attach setFile(Long file) {
        this.file = file;
        return this;
    }

}
