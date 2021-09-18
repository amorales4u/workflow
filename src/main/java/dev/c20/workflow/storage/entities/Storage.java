package dev.c20.workflow.storage.entities;


import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.commons.tools.PathUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@Accessors(chain = true)
@Entity
@Inheritance(strategy= InheritanceType.JOINED)
@Table(name= WorkflowApplication.DB_PREFIX + "STG")
public class Storage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=WorkflowApplication.DB_PREFIX + "STG")
    private Long id;

    @Column(name=WorkflowApplication.DB_PREFIX + "IS_FOLDER" , columnDefinition = "TINYINT")
    private Boolean isFolder = null;

    @Column(name=WorkflowApplication.DB_PREFIX + "NAME" )
    private String name;

    @Column(name=WorkflowApplication.DB_PREFIX + "EXTENSION" )
    private String extension;

    @Column(name=WorkflowApplication.DB_PREFIX + "IMAGE")
    private String image;

    @Column(name=WorkflowApplication.DB_PREFIX + "CREATED")
    private Date created;

    @Column(name=WorkflowApplication.DB_PREFIX + "CREATOR")
    private String creator;

    @Column(name=WorkflowApplication.DB_PREFIX + "DATE_ASSIGNED")
    private Date dateAssigned;

    @Column(name=WorkflowApplication.DB_PREFIX + "ASSIGNED")
    private String assigned;


    @Column(name=WorkflowApplication.DB_PREFIX + "DELETED", columnDefinition = "TINYINT" )
    private Boolean deleted = null;

    @Column(name=WorkflowApplication.DB_PREFIX + "DELETE_DATE")
    @Temporal(value= TemporalType.TIMESTAMP)
    private Date deletedDate;

    @Column(name=WorkflowApplication.DB_PREFIX + "DELETER")
    private String userDeleter;

    @Column(name=WorkflowApplication.DB_PREFIX + "MODIFIED", columnDefinition = "TINYINT" )
    private Boolean modified = null;

    @Column(name=WorkflowApplication.DB_PREFIX + "MODIFY_DATE")
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date modifyDate;

    @Column(name=WorkflowApplication.DB_PREFIX + "MODIFIER")
    private String modifier;

    @Column(name=WorkflowApplication.DB_PREFIX + "FILE_ID")
    private Long fileId;

    @Column(name=WorkflowApplication.DB_PREFIX + "READONLY", columnDefinition = "TINYINT" )
    private Boolean readOnly = false;

    @Column(name=WorkflowApplication.DB_PREFIX + "VISIBLE", columnDefinition = "TINYINT" )
    private Boolean visible = true;

    @Column(name=WorkflowApplication.DB_PREFIX + "LOCKED", columnDefinition = "TINYINT" )
    private Boolean locked = false;

    @Column(name=WorkflowApplication.DB_PREFIX + "RESTRICTED_BY_ROLE", columnDefinition = "TINYINT" )
    private Boolean restrictedByPerm = false;

    @Column(name=WorkflowApplication.DB_PREFIX + "RESTRICTED_CHILDREN_BY_ROLE", columnDefinition = "TINYINT" )
    private Boolean childrenRestrictedByPerm = false;

    @Column(name=WorkflowApplication.DB_PREFIX + "STATUS")
    private Integer status;

    @Column(name=WorkflowApplication.DB_PREFIX + "CLAZZ_NAME")
    private String clazzName;

    @Column(name=WorkflowApplication.DB_PREFIX + "LEVEL")
    private Integer level = 0;

    @Column(name=WorkflowApplication.DB_PREFIX + "DESCRIPTION", length = 200)
    private String description;

    @Column(name=WorkflowApplication.DB_PREFIX + "PATH",length = 1000 )
    private String path;

    @Transient
    private List<Storage> children = new ArrayList<>();

    public List<Storage> getChildren() {
        return children;
    }

    public Storage setChildren(List<Storage> children) {
        this.children = children;
        return this;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Storage storage = (Storage) o;
        return Objects.equals(id, storage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Storage setPath(String path) {
        this.path = path;
        this.isFolder = PathUtils.isFolder(path);
        this.name = PathUtils.getName(path);
        this.extension = PathUtils.getExtension(path);
        this.level = PathUtils.getPathLevel(path);
        return this;
    }

    public Storage setPropertiesFrom( Storage source ) {
        return this.setAssigned(source.assigned)
                .setExtension(source.extension)
                .setDescription(source.description)
                .setChildrenRestrictedByPerm(source.childrenRestrictedByPerm)
                .setClazzName(source.clazzName)
                .setImage(source.image)
                .setLocked(source.locked)
                .setReadOnly(source.readOnly)
                .setRestrictedByPerm(source.restrictedByPerm)
                .setIsFolder(source.isFolder)
                .setVisible(source.visible);
    }
}
