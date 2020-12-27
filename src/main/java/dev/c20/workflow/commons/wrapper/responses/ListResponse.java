package dev.c20.workflow.commons.wrapper.responses;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ListResponse<T> {

    private boolean error = false;
    private String errorDescription = "";
    private List<T> data = new ArrayList<>();
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

    public ListResponse(T obj) {
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

    public ListResponse(List<T> objs, int currentPage, int pageCount) {
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

    public ListResponse setResponse(String data) {
        if (data != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                ListResponse<T> res =
                        mapper.readValue(data, new TypeReference<ListResponse<T>>() {
                        });

                this.error = res.error;
                this.errorDescription = res.errorDescription;
                this.data = res.data;
                this.listCount = res.listCount;
                this.currentPage = res.currentPage;
                this.pageCount = res.pageCount;

                return this;
            } catch (Exception ex) {
                this.error = true;
                this.errorDescription = "Error al parsear el body:" + ex.getMessage();
            }
        }

        return this;
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

    public List<T> getData() {
        return data;
    }

    public ListResponse setData(List<T> data) {
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
