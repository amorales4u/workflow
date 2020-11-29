package dev.c20.workflow.entities.adds;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.entities.Storage;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name= WorkflowApplication.DB_PREFIX + "STG_PERM")
public class Perm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=WorkflowApplication.DB_PREFIX + "ID")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn( name=WorkflowApplication.DB_PREFIX + "STORAGE")
    private Storage parent;


    @Column(name=WorkflowApplication.DB_PREFIX + "USER", length = 10)
    private String user;

    @Column(name=WorkflowApplication.DB_PREFIX + "CREATE")
    private Boolean canCreate;

    @Column(name=WorkflowApplication.DB_PREFIX + "READ")
    private Boolean canRead;

    @Column(name=WorkflowApplication.DB_PREFIX + "UPDATE")
    private Boolean canUpdate;

    @Column(name=WorkflowApplication.DB_PREFIX + "DELETE")
    private Boolean canDelete;

    @Column(name=WorkflowApplication.DB_PREFIX + "ADMIN")
    private Boolean canAdmin;

    @Column(name=WorkflowApplication.DB_PREFIX + "SEND")
    private Boolean canSend;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perm perm = (Perm) o;
        return id.equals(perm.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public Perm setId(Long id) {
        this.id = id;
        return this;
    }

    public Storage getParent() {
        return parent;
    }

    public Perm setParent(Storage parent) {
        this.parent = parent;
        return this;
    }

    public String getUser() {
        return user;
    }

    public Perm setUser(String user) {
        this.user = user;
        return this;
    }

    public Boolean getCanCreate() {
        return canCreate;
    }

    public Perm setCanCreate(Boolean canCreate) {
        this.canCreate = canCreate;
        return this;
    }

    public Boolean getCanUpdate() {
        return canUpdate;
    }

    public Perm setCanUpdate(Boolean canUpdate) {
        this.canUpdate = canUpdate;
        return this;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public Perm setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
        return this;
    }

    public Boolean getCanAdmin() {
        return canAdmin;
    }

    public Perm setCanAdmin(Boolean canAdmin) {
        this.canAdmin = canAdmin;
        return this;
    }
    public Boolean getCanRead() {
        return canRead;
    }

    public Perm setCanRead(Boolean canRead) {
        this.canRead = canRead;
        return this;
    }

    public Boolean getCanSend() {
        return canSend;
    }

    public Perm setCanSend(Boolean canSend) {
        this.canSend = canSend;
        return this;
    }


}
