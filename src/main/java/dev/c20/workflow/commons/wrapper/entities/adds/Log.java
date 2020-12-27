package dev.c20.workflow.commons.wrapper.entities.adds;

import dev.c20.workflow.commons.wrapper.entities.Storage;

import java.util.Date;
import java.util.Objects;

public class Log {

    private Long id;

    private Storage parent;

    private Date modified;

    private String modifier;

    private String comment;

    private Long commentId = 0l;

    private Long type = 0l;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return id.equals(log.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public Log setId(Long id) {
        this.id = id;
        return this;
    }

    public Storage getParent() {
        return parent;
    }

    public Log setParent(Storage parent) {
        this.parent = parent;
        return this;
    }

    public Date getModified() {
        return modified;
    }

    public Log setModified(Date modified) {
        this.modified = modified;
        return this;
    }

    public String getModifier() {
        return modifier;
    }

    public Log setModifier(String modifier) {
        this.modifier = modifier;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Log setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Long getCommentId() {
        return commentId;
    }

    public Log setCommentId(Long commentId) {
        this.commentId = commentId;
        return this;
    }

    public Long getType() {
        return type;
    }

    public Log setType(Long type) {
        this.type = type;
        return this;
    }
}
