package dev.c20.workflow.entities.adds;

import dev.c20.workflow.app.WorkflowApplication;
import dev.c20.workflow.entities.Storage;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name= WorkflowApplication.DB_PREFIX + "STG_PERM")
class Permition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=WorkflowApplication.DB_PREFIX + "ID")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn( name=WorkflowApplication.DB_PREFIX + "STORAGE")
    private Storage parent;


    @Column(name=WorkflowApplication.DB_PREFIX + "USER", length = 10)
    private String user;

    @Column(name=WorkflowApplication.DB_PREFIX + "CREATE", length = 10)
    private Boolean canCreate;

    @Column(name=WorkflowApplication.DB_PREFIX + "UPDATE", length = 10)
    private Boolean canUpdate;

    @Column(name=WorkflowApplication.DB_PREFIX + "DELETE", length = 10)
    private Boolean canDelete;

    @Column(name=WorkflowApplication.DB_PREFIX + "ADMIN", length = 10)
    private Boolean canAdmin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permition permition = (Permition) o;
        return id.equals(permition.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public Permition setId(Long id) {
        this.id = id;
        return this;
    }

    public Storage getParent() {
        return parent;
    }

    public Permition setParent(Storage parent) {
        this.parent = parent;
        return this;
    }

    public String getUser() {
        return user;
    }

    public Permition setUser(String user) {
        this.user = user;
        return this;
    }

    public Boolean getCanCreate() {
        return canCreate;
    }

    public Permition setCanCreate(Boolean canCreate) {
        this.canCreate = canCreate;
        return this;
    }

    public Boolean getCanUpdate() {
        return canUpdate;
    }

    public Permition setCanUpdate(Boolean canUpdate) {
        this.canUpdate = canUpdate;
        return this;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public Permition setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
        return this;
    }

    public Boolean getCanAdmin() {
        return canAdmin;
    }

    public Permition setCanAdmin(Boolean canAdmin) {
        this.canAdmin = canAdmin;
        return this;
    }
}
