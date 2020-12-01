package dev.c20.workflow.storage.entities.adds;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.storage.entities.Storage;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name=WorkflowApplication.DB_PREFIX + "STG_LOG")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=WorkflowApplication.DB_PREFIX + "ID")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn( name= WorkflowApplication.DB_PREFIX + "STORAGE")
    private Storage parent;

    @Column(name=WorkflowApplication.DB_PREFIX + "MODIFIED")
    @Temporal(value=TemporalType.TIMESTAMP)
    //@DateBridge(resolution=Resolution.SECOND)
    private Date modified;

    @Column(name=WorkflowApplication.DB_PREFIX + "MODIFIER", length=2000)
    private String modifier;

    @Column(name=WorkflowApplication.DB_PREFIX + "COMMENT", length=2000)
    private String comment;

    @Column(name=WorkflowApplication.DB_PREFIX + "COMMENT_ID")
    private Long commentId = 0l;

    @Column(name=WorkflowApplication.DB_PREFIX + "TYPE")
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
