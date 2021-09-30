package dev.c20.workflow.storage.entities.adds;


import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.storage.entities.Storage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;


@Setter
@Getter
@Accessors(chain = true)
@Entity
@Table(name= WorkflowApplication.DB_PREFIX + "STG_NOTE")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=WorkflowApplication.DB_PREFIX + "ID")
    private Long id;

    @ManyToOne( fetch=FetchType.LAZY)
    @JoinColumn( name=WorkflowApplication.DB_PREFIX + "STORAGE")
    private Storage parent;

    @Column( name=WorkflowApplication.DB_PREFIX + "CREATOR")
    private String creator;

    @Column(name=WorkflowApplication.DB_PREFIX + "CREATED")
    private Date created;

    @Column(name=WorkflowApplication.DB_PREFIX + "IMAGE")
    private String image;

    @Column(name=WorkflowApplication.DB_PREFIX + "COMMENT", length=2000)
    private String comment;


    public boolean equals(Object o) {
        if (!(o instanceof Note)) return false;

        Note obj = (Note) o;

        if (id != obj.id) return false;

        return true;
    }

    public int hashCode() {

        return (id != null ? id.hashCode() : 0);
    }

}
