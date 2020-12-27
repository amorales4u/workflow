package dev.c20.workflow.commons.wrapper.responses;

import dev.c20.workflow.commons.wrapper.entities.Storage;

import java.util.Map;

public class TaskInstance {
    private int error = 0;
    private String errorDescription;
    private Storage task;
    private Map<String,Object> data;

    public int getError() {
        return error;
    }

    public TaskInstance setError(int error) {
        this.error = error;
        return this;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public TaskInstance setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
        return this;
    }

    public Storage getTask() {
        return task;
    }

    public TaskInstance setTask(Storage task) {
        this.task = task;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public TaskInstance setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }
}
