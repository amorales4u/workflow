package dev.c20.workflow.services.responses;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ListResponse {

    private boolean error = false;
    private String errorDescription = "";
    private List<Object> data = new ArrayList<>();
    private int listCount = 0;
    private int currentPage = 0;
    private int pageCount = 10;

    public ListResponse( ) {

    }

    public ListResponse(String errorDescription) {
        this.error = true;
        this.errorDescription = errorDescription;
        return;
    }

    public ListResponse(Object obj) {
        if( obj == null ) {
            this.error = true;
            this.errorDescription = "No existe";
            return;
        }

        this.data.add(obj);
        this.listCount = 1;
        this.currentPage = 1;
        this.pageCount = 1;
    }

    public ListResponse(List<Object> objs, int currentPage, int pageCount) {
        if( objs == null ) {
            this.error = true;
            this.errorDescription = "No hay información";
            return;
        }

        this.data = objs;
        this.listCount = objs.size();
        this.currentPage = currentPage;
        this.pageCount = pageCount;
    }

    public ResponseEntity<ListResponse> response() {

        return this.error ? error() : ok();
    }

    public ResponseEntity<ListResponse> ok() {
        return ResponseEntity.ok(this);
    }

    public ResponseEntity<ListResponse> error() {
        return ResponseEntity.badRequest().body(this);
    }

    public boolean isError() {
        return error;
    }

    public ListResponse setError(boolean error) {
        this.error = error;
        return this;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public ListResponse setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
        return this;
    }

    public List<Object> getData() {
        return data;
    }

    public ListResponse setData(List<Object> data) {
        this.data = data;
        return this;
    }

    public int getListCount() {
        return listCount;
    }

    public ListResponse setListCount(int listCount) {
        this.listCount = listCount;
        return this;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public ListResponse setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public int getPageCount() {
        return pageCount;
    }

    public ListResponse setPageCount(int pageCount) {
        this.pageCount = pageCount;
        return this;
    }
}
