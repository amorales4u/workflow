package dev.c20.workflow.storage.entities.adds;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.storage.entities.Storage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Objects;


@Setter
@Getter
@Accessors(chain = true)
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

}
