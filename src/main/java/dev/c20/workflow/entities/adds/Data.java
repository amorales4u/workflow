package dev.c20.workflow.entities.adds;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.entities.Storage;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name= WorkflowApplication.DB_PREFIX + "STG_DATA")
public class Data implements Serializable {

    @Id
    @ManyToOne(targetEntity= Storage.class)
    @JoinColumn( name=WorkflowApplication.DB_PREFIX + "STORAGE")
    private Storage parent;

    @Column(name=WorkflowApplication.DB_PREFIX + "VALUE")
    private String data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data value = (Data) o;
        return parent.equals(value.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent);
    }

    public Storage getParent() {
        return parent;
    }

    public Data setParent(Storage parent) {
        this.parent = parent;
        return this;
    }

    public String getData() {
        return data;
    }

    public Data setData(String value) {
        this.data = value;
        return this;
    }
}
