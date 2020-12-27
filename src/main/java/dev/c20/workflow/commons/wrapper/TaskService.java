package dev.c20.workflow.commons.wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.c20.workflow.commons.wrapper.responses.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TaskService {

    @Autowired
    StorageRestCall storageRestCall;

    String targetContext;
    String auth;

    public void init(String server, String targetContext, String auth) {
        storageRestCall.setServer(server);
        this.targetContext = targetContext;
        this.auth = auth;
    }

    public TaskInstance callStorageService(HttpMethod httpMethod, String path, Object body) {
        storageRestCall
                .setHttpMethod(httpMethod)
                .setWebContext(this.targetContext + path)
                .setHeader("Authorization",auth)
                .setBody(body)
                .send();

        try {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(storageRestCall.getResponse(),TaskInstance.class);

        } catch ( Exception ex ) {
            TaskInstance response = new TaskInstance();
            response.setError(-1);
            response.setErrorDescription("Error al leer el body:" + ex.getMessage());
            return response;
        }
    }

    public TaskInstance start(String path, Map<String,Object> data ) {

        return callStorageService(HttpMethod.POST,this.targetContext + "/task" + path,data);
    }

    public TaskInstance update(String path, Map<String,Object> data ) {

        return callStorageService(HttpMethod.POST,this.targetContext + "/task" + path,data);
    }

    public TaskInstance delete(String path, Map<String,Object> data ) {

        return callStorageService(HttpMethod.POST,this.targetContext + "/task" + path,data);
    }

    public TaskInstance complete(String path, Map<String,Object> data ) {

        return callStorageService(HttpMethod.POST,this.targetContext + "/task" + path,data);
    }


}
