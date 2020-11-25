package dev.c20.workflow.entities;


import dev.c20.workflow.WorkflowApplication;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
@Table(name= WorkflowApplication.DB_PREFIX + "STG")

public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=WorkflowApplication.DB_PREFIX + "STG")
    private Long id;

    @Column(name=WorkflowApplication.DB_PREFIX + "IS_FILE" , columnDefinition = "TINYINT")
    private Boolean isFile = false;

    @Column(name=WorkflowApplication.DB_PREFIX + "NAME" )
    private String name;

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
    private Boolean deleted = false;

    @Column(name=WorkflowApplication.DB_PREFIX + "DELETE_DATE")
    @Temporal(value= TemporalType.TIMESTAMP)
    private Date deletedDate;

    @Column(name=WorkflowApplication.DB_PREFIX + "DELETER")
    private String userDeleter;

    @Column(name=WorkflowApplication.DB_PREFIX + "MODIFIED", columnDefinition = "TINYINT" )
    private Boolean modified = false;

    @Column(name=WorkflowApplication.DB_PREFIX + "MODIFY_DATE")
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date modifyDate;

    @Column(name=WorkflowApplication.DB_PREFIX + "MODIFIER")
    private String modifier;

    @Column(name=WorkflowApplication.DB_PREFIX + "FILE")
    private Long file;

    @Column(name=WorkflowApplication.DB_PREFIX + "READONLY", columnDefinition = "TINYINT" )
    private Boolean readOnly = false;

    @Column(name=WorkflowApplication.DB_PREFIX + "VISIBLE", columnDefinition = "TINYINT" )
    private Boolean visible = true;

    @Column(name=WorkflowApplication.DB_PREFIX + "LOCKED", columnDefinition = "TINYINT" )
    private Boolean locked = false;

    @Column(name=WorkflowApplication.DB_PREFIX + "ROLE", columnDefinition = "TINYINT" )
    private Boolean restrictedByRole = false;

    @Column(name=WorkflowApplication.DB_PREFIX + "CHILDREN_ROLE", columnDefinition = "TINYINT" )
    private Boolean childrenRestrictedByRole = false;

    @Column(name=WorkflowApplication.DB_PREFIX + "STATUS")
    private Integer status;

    @Column(name=WorkflowApplication.DB_PREFIX + "CLAZZ_NAME")
    private String clazzName;

    @Column(name=WorkflowApplication.DB_PREFIX + "LEVEL")
    private Integer level = 0;

    @Column(name=WorkflowApplication.DB_PREFIX + "PATH",length = 1000 )
    private String path;

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

    public Long getId() {
        return id;
    }

    public Storage setId(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getFile() {
        return isFile;
    }

    public Storage setFile(Long file) {
        this.file = file;
        return this;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public Storage setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    public Boolean getVisible() {
        return visible;
    }

    public Storage setVisible(Boolean visible) {
        this.visible = visible;
        return this;
    }

    public Boolean getLocked() {
        return locked;
    }

    public Storage setLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public Boolean getRestrictedByRole() {
        return restrictedByRole;
    }

    public Storage setRestrictedByRole(Boolean restrictedByRole) {
        this.restrictedByRole = restrictedByRole;
        return this;
    }

    public Boolean getChildrenRestrictedByRole() {
        return childrenRestrictedByRole;
    }

    public Storage setChildrenRestrictedByRole(Boolean childrenRestrictedByRole) {
        this.childrenRestrictedByRole = childrenRestrictedByRole;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Storage setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getClazzName() {
        return clazzName;
    }

    public Storage setClazzName(String clazzName) {
        this.clazzName = clazzName;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public Storage setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Storage setPath(String path) {
        this.path = path;
        return this;
    }

    public Storage setFile(Boolean file) {
        isFile = file;
        return this;
    }

    public String getName() {
        return name;
    }

    public Storage setName(String name) {
        this.name = name;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Storage setImage(String image) {
        this.image = image;
        return this;
    }

    public Date getCreated() {
        return created;
    }

    public Storage setCreated(Date created) {
        this.created = created;
        return this;
    }

    public Date getDateAssigned() {
        return dateAssigned;
    }

    public Storage setAssigned(String assigned) {
        this.assigned = assigned;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public Storage setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Storage setDeleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public Storage setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
        return this;
    }

    public String getUserDeleter() {
        return userDeleter;
    }

    public Storage setUserDeleter(String userDeleter) {
        this.userDeleter = userDeleter;
        return this;
    }

    public Boolean getModified() {
        return modified;
    }

    public Storage setModified(Boolean modified) {
        this.modified = modified;
        return this;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public Storage setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
        return this;
    }

    public String getModifier() {
        return modifier;
    }

    public Storage setModifier(String modifier) {
        this.modifier = modifier;
        return this;
    }
}
