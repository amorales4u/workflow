package dev.c20.workflow.commons.wrapper.entities.adds;

import dev.c20.workflow.commons.wrapper.entities.Storage;

import java.util.Objects;

public class Perm {

    private Long id;

    private Storage parent;

    private String user;

    private Boolean canCreate;

    private Boolean canRead;

    private Boolean canUpdate;

    private Boolean canDelete;

    private Boolean canAdmin;

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
