package dev.c20.workflow.commons.storage;

import dev.c20.workflow.commons.storage.entities.Storage;
import dev.c20.workflow.commons.storage.entities.adds.*;
import dev.c20.workflow.commons.storage.responses.ListResponse;
import dev.c20.workflow.commons.storage.responses.ObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class StorageService {

    @Autowired
    StorageRestCall storageRestCall;

    String targetContext;
    public void init(String server, String targetContext) {
        storageRestCall.setServer(server);
        this.targetContext = targetContext;
    }

    public void callStorageService(HttpMethod httpMethod, String path, Object body) {
        storageRestCall
                .setHttpMethod(httpMethod)
                .setWebContext(this.targetContext + path)
                .setHeader("Authorization","token VPqM7akss8ifIkjz0bA0eyHNo3N8PAkH02iKoEZ48sIkwxBevF+Wjddr7zgfx1H9ji8+wmZAI8v5p2z94fHyW6sKpuGfBq6GjR7aV2QKP5Dd5XKhSxpWeMoz6F8/z/NFBQv4GUO4drWE9eTXHNQI9/1pX7f5xsXXOU/eRYwts5k7XVhc4PjV8YuJVuC820f5W+90RTufkoWdu8aHba1t/g==")
                .setBody(body)
                .send();
    }

    public ObjectResponse<Storage> createFolder(String path, Storage storage ) {
        callStorageService(HttpMethod.POST,this.targetContext + "/folder" + path,storage);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Storage> readFolder(String path ) {
        callStorageService(HttpMethod.GET,this.targetContext + "/folder" + path,null);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Storage> updateFolder(String path, Storage storage ) {
        callStorageService(HttpMethod.PUT,this.targetContext + "/folder" + path,storage);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Storage> deleteFolder(String path ) {
        callStorageService(HttpMethod.DELETE,this.targetContext + "/folder" + path,null);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);
    }

    // files
    public ObjectResponse<Storage> createFile(String path,Storage storage ) {
        callStorageService(HttpMethod.POST,this.targetContext + "/file" + path,storage);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Storage> readFile(String path ) {
        callStorageService(HttpMethod.GET,this.targetContext + "/file" + path,null);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);

    }

    public ObjectResponse<Storage> updateFile(String path, Storage storage ) {
        callStorageService(HttpMethod.PUT,this.targetContext + "/file" + path,storage);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Storage> deleteFile(String path ) {
        callStorageService(HttpMethod.DELETE,this.targetContext + "/file" + path,null);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);
    }

    // attachments
    public ObjectResponse<Attach> createAttach(String path, Attach attach ) {
        callStorageService(HttpMethod.POST,this.targetContext + "/attach" + path,attach);
        return (new ObjectResponse<Attach>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Attach> updateAttach(String path, Attach attach ) {
        callStorageService(HttpMethod.PUT,this.targetContext + "/attach" + path,attach);
        return (new ObjectResponse<Attach>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Attach> deleteAttach(String path, Attach attach ) {
        callStorageService(HttpMethod.DELETE,this.targetContext + "/attach" + path,attach);
        return (new ObjectResponse<Attach>()).setResponse(storageRestCall.response);
    }

    public ListResponse<Attach> getAttachments(String path ) {
        callStorageService(HttpMethod.DELETE,this.targetContext + "/attach" + path,null);
        return (new ListResponse<Attach>()).setResponse(storageRestCall.response);
    }

    // data
    public ObjectResponse<Data> createData(String path, Data data ) {
        callStorageService(HttpMethod.POST,this.targetContext + "/data" + path,data);
        return (new ObjectResponse<Data>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Data> getData(String path ) {
        callStorageService(HttpMethod.POST,this.targetContext + "/data" + path,null);
        return (new ObjectResponse<Data>()).setResponse(storageRestCall.response);
    }

    // log
    public ObjectResponse<Log> createLog(String path, Log log ) {
        callStorageService(HttpMethod.POST,this.targetContext + "/log" + path,log);
        return (new ObjectResponse<Log>()).setResponse(storageRestCall.response);
    }

    public ListResponse<Log> getLog(String path ) {
        callStorageService(HttpMethod.POST,this.targetContext + "/log" + path,null);
        return (new ListResponse<Data>()).setResponse(storageRestCall.response);
    }

    // notes
    public ObjectResponse<Note> createNote(String path, Note note ) {
        callStorageService(HttpMethod.POST,this.targetContext + "/note" + path,note);
        return (new ObjectResponse<Note>()).setResponse(storageRestCall.response);
    }

    public ListResponse<Note> getNotes(String path ) {
        callStorageService(HttpMethod.POST,this.targetContext + "/note" + path,null);
        return (new ListResponse<Note>()).setResponse(storageRestCall.response);
    }

    // values
    public ObjectResponse<Value> createValue(String path, Value value ) {
        callStorageService(HttpMethod.POST,this.targetContext + "/value" + path,value);
        return (new ObjectResponse<Value>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Value> updateValue(String path, Value value ) {
        callStorageService(HttpMethod.PUT,this.targetContext + "/value" + path,value);
        return (new ObjectResponse<Value>()).setResponse(storageRestCall.response);
    }

    public ListResponse<Value> getValues(String path ) {
        callStorageService(HttpMethod.GET,this.targetContext + "/value" + path,null);
        return (new ListResponse<Value>()).setResponse(storageRestCall.response);
    }


}
