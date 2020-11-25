package dev.c20.workflow.services;

import dev.c20.workflow.entities.Storage;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class StorageResponse {

    private boolean error = false;
    private String errorDescription = "";
    private List<Storage> storages = new ArrayList<>();
    private int listCount = 0;
    private int currentPage = 0;
    private int pageCount = 10;

    public StorageResponse( Storage storage) {
        if( storage == null ) {
            this.error = true;
            this.errorDescription = "No existe";
            return;
        }

        this.storages.add(storage);
        this.listCount = 1;
        this.currentPage = 1;
        this.pageCount = 1;
    }

    public StorageResponse( List<Storage> storages, int currentPage, int pageCount) {
        if( storages == null ) {
            this.error = true;
            this.errorDescription = "No hay información";
            return;
        }

        this.storages = storages;
        this.listCount = storages.size();
        this.currentPage = currentPage;
        this.pageCount = pageCount;
    }

    public ResponseEntity<StorageResponse> ok() {
        return ResponseEntity.ok(this);
    }

    public ResponseEntity<StorageResponse> error() {
        return ResponseEntity.badRequest().body(this);
    }

    public boolean isError() {
        return error;
    }

    public StorageResponse setError(boolean error) {
        this.error = error;
        return this;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public StorageResponse setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
        return this;
    }

    public List<Storage> getStorages() {
        return storages;
    }

    public StorageResponse setStorages(List<Storage> storages) {
        this.storages = storages;
        return this;
    }

    public int getListCount() {
        return listCount;
    }

    public StorageResponse setListCount(int listCount) {
        this.listCount = listCount;
        return this;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public StorageResponse setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public int getPageCount() {
        return pageCount;
    }

    public StorageResponse setPageCount(int pageCount) {
        this.pageCount = pageCount;
        return this;
    }
}
