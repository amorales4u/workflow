package dev.c20.workflow.commons.wrapper.entities.adds;

import java.io.Serializable;
import java.util.Objects;

public class Data implements Serializable {

    private Long parent;

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
