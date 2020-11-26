package dev.c20.workflow.entities.adds;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.entities.Storage;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name= WorkflowApplication.DB_PREFIX + "STG_VALUE")
public class Value {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=WorkflowApplication.DB_PREFIX + "ID")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn( name=WorkflowApplication.DB_PREFIX + "STORAGE")
    private Storage parent;

    @Column(name=WorkflowApplication.DB_PREFIX + "NAME")
    private String name;

    @Column(name=WorkflowApplication.DB_PREFIX + "VALUE")
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value value = (Value) o;
        return id.equals(value.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public Value setId(Long id) {
        this.id = id;
        return this;
    }

    public Storage getParent() {
        return parent;
    }

    public Value setParent(Storage parent) {
        this.parent = parent;
        return this;
    }

    public String getName() {
        return name;
    }

    public Value setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Value setValue(String value) {
        this.value = value;
        return this;
    }
}
