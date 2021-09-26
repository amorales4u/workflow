package dev.c20.workflow.storage.entities.adds;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.storage.entities.Storage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@Accessors(chain = true)
@Entity
@Table(name= WorkflowApplication.DB_PREFIX + "STG_VALUE")
public class Value {

    public static int VALUE_IS_PROPERTY = 1000;
    public static int VALUE_IS_PROPERTY_MASKED = 1010;
    public static int VALUE_IS_PROPERTY_PROTECTTED = 1020;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=WorkflowApplication.DB_PREFIX + "ID")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn( name=WorkflowApplication.DB_PREFIX + "STORAGE")
    private Storage parent;

    @Column(name=WorkflowApplication.DB_PREFIX + "NAME")
    private String name;

    @Column(name=WorkflowApplication.DB_PREFIX + "STR_VALUE")
    private String value;

    @Column(name=WorkflowApplication.DB_PREFIX + "STR_TYPE")
    private String type;

    @Column(name=WorkflowApplication.DB_PREFIX + "NUM_VALUE", precision = 6)
    private Double doubleValue;

    @Column(name=WorkflowApplication.DB_PREFIX + "DATE_VALUE")
    private Date dateValue;

    @Column(name=WorkflowApplication.DB_PREFIX + "LONG_VALUE")
    private Long longValue;

    @Column(name=WorkflowApplication.DB_PREFIX + "INT_VALUE")
    private Integer intValue;

    @Column(name=WorkflowApplication.DB_PREFIX + "ORDER_VALUE")
    private Integer order;


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

}
