package dev.c20.workflow.commons.wrapper.entities;


import dev.c20.workflow.commons.tools.PathUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Storage implements Serializable {

    private Long id;

    private Boolean isFolder = null;

    private String name;

    private String extension;

    private String image;

    private Date created;

    private String creator;

    private Date dateAssigned;

    private String assigned;

    private Boolean deleted = null;

    private Date deletedDate;

    private String userDeleter;

    private Boolean modified = null;

    private Date modifyDate;

    private String modifier;

    private Long fileId;

    private Boolean readOnly = false;

    private Boolean visible = true;

    private Boolean locked = false;

    private Boolean restrictedByPerm = false;

    private Boolean childrenRestrictedByPerm = false;

    private Integer status;

    private String clazzName;

    private Integer level = 0;

    private String description;

    private String path;

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

    public Long getId() {
        return id;
    }

    public Storage setId(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getIsFolder() {
        return isFolder;
    }

    public Long getFileId() {
        return this.fileId;
    }
    public Storage setFileId(Long fileId) {
        this.fileId = fileId;
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

    public Boolean getRestrictedByPerm() {
        return restrictedByPerm;
    }

    public Storage setRestrictedByPerm(Boolean restrictedByRole) {
        this.restrictedByPerm = restrictedByRole;
        return this;
    }

    public Boolean getChildrenRestrictedByPerm() {
        return childrenRestrictedByPerm;
    }

    public Storage setChildrenRestrictedByPerm(Boolean childrenRestrictedByRole) {
        this.childrenRestrictedByPerm = childrenRestrictedByRole;
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
        this.isFolder = PathUtils.isFolder(path);
        this.name = PathUtils.getName(path);
        this.extension = PathUtils.getExtension(path);
        this.level = PathUtils.getPathLevel(path);
        this.created = new Date();
        return this;
    }

    public Storage setIsFolder(Boolean folder) {
        isFolder = folder;
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

    public Boolean getFolder() {
        return isFolder;
    }

    public Storage setFolder(Boolean folder) {
        isFolder = folder;
        return this;
    }

    public String getExtension() {
        return extension;
    }

    public Storage setExtension(String extension) {
        this.extension = extension;
        return this;
    }

    public Storage setDateAssigned(Date dateAssigned) {
        this.dateAssigned = dateAssigned;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Storage setDescription(String description) {
        this.description = description;
        return this;
    }



    public String getAssigned() {
        return assigned;
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
                .setVisible(source.visible);
    }
}
