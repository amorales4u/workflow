package dev.c20.workflow.commons.wrapper.entities.adds;

import dev.c20.workflow.commons.wrapper.entities.Storage;

import java.util.Date;
import java.util.Objects;

public class Value {

    private Long id;

    private Storage parent;

    private String name;

    private String value;

    private Double doubleValue;

    private Date dateValue;

    private Long longValue;


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

    public Double getDoubleValue() {
        return doubleValue;
    }

    public Value setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
        return this;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public Value setDateValue(Date dateValue) {
        this.dateValue = dateValue;
        return this;
    }

    public Long getLongValue() {
        return longValue;
    }

    public Value setLongValue(Long longValue) {
        this.longValue = longValue;
        return this;
    }


}
