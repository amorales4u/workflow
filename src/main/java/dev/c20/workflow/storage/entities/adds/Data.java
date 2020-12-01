package dev.c20.workflow.storage.entities.adds;

import dev.c20.workflow.WorkflowApplication;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name= WorkflowApplication.DB_PREFIX + "STG_DATA")
public class Data implements Serializable {

    @Id
    @Column( name=WorkflowApplication.DB_PREFIX + "STORAGE")
    private Long parent;

    @Column(name=WorkflowApplication.DB_PREFIX + "VALUE", length = 32000)
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

    public Long getParent() {
        return parent;
    }

    public Data setParent(Long parent) {
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
