package dev.c20.workflow.storage.entities.adds;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.storage.entities.Storage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)

@Entity
@Table(name= WorkflowApplication.DB_PREFIX + "STG_DATA")
public class Data implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=WorkflowApplication.DB_PREFIX + "ID")
    private Long id;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn( name= WorkflowApplication.DB_PREFIX + "STORAGE")
    private Storage parent;

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

}
