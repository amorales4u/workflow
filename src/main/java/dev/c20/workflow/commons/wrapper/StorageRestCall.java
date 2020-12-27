package dev.c20.workflow.commons.wrapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.c20.workflow.commons.wrapper.entities.Storage;
import dev.c20.workflow.commons.wrapper.responses.ObjectResponse;
import dev.c20.workflow.commons.tools.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class StorageRestCall {

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

    String server = null;
    String webContext = null;
    Object body = null;
    String response = null;
    HttpMethod httpMethod = null;
    HttpRequestBase requestBase;

    public StorageRestCall( String server ) {
        this.server = server;
    }
    public StorageRestCall(  ) {

    }

    public StorageRestCall setWebContext(String webContext) {
        this.webContext = webContext;
        createRequest();
        return this;
    }

    public StorageRestCall setBody(Object body) {
        this.body = body;
        return this;
    }

    public StorageRestCall setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        createRequest();
        return this;
    }

    public StorageRestCall setServer(String server) {
        this.server = server;
        createRequest();
        return this;
    }

    public void createRequest() {
        if( this.server != null && this.webContext != null && httpMethod != null) {
            if( httpMethod == HttpMethod.GET ) {
                requestBase = (HttpRequestBase) new HttpGet(this.server + this.webContext);
            } else if( httpMethod == HttpMethod.PUT ) {
                requestBase = (HttpRequestBase) new HttpPut(this.server + this.webContext);
            } else if( httpMethod == HttpMethod.POST ) {
                requestBase = (HttpRequestBase) new HttpPost(this.server + this.webContext);
            } else if( httpMethod == HttpMethod.DELETE ) {
                requestBase = (HttpRequestBase) new HttpDelete(this.server + this.webContext);
            }

        }

    }
    public StorageRestCall setHeader(String header, String value) {
        requestBase.setHeader(header,value);
        return this;
    }

    public StorageRestCall sendWithNoWait() {
        return this;
    }
    int statusCode = 0;
    public int getStatusCode() {
        return this.statusCode;
    }

    public StorageRestCall send() {

        try {

            logger.info("Sending message to:" +this.server +  webContext);

            int timeout = 1000;
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout)
                    .setSocketTimeout(timeout).build();

            CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

            logger.info( "Set entity:"+StringUtils.toJSON(body));
            StringEntity entity = new StringEntity(StringUtils.toJSON(body));
            if( requestBase instanceof HttpEntityEnclosingRequestBase ) {
                ((HttpEntityEnclosingRequestBase)requestBase).setEntity(entity);
            }
            requestBase.setHeader("Accept", "application/json");
            requestBase.setHeader("Content-type", "application/json");
            /*
            if( httpRequest != null )
                httpPost.setHeader(WorkflowApplication.HEADER_AUTHORIZATION, httpRequest.getHeader(WorkflowApplication.HEADER_AUTHORIZATION));
            */
            Long start = System.currentTimeMillis();
            logger.info("Send REST call");
            CloseableHttpResponse response = client.execute(requestBase);
            String result = null;
            statusCode = response.getStatusLine().getStatusCode();
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
            client.close();
            logger.info("Sended message to:" + webContext + " in " + (System.currentTimeMillis() - start) + " ms");

            this.response = result;
        } catch (Exception ex) {
            logger.error(ex);
        }

        return this;
    }

    public String getResponse() {
        return response;
    }

    Class responseClassType;
    public StorageRestCall setResponseType(Class classType) {
        this.responseClassType = classType;
        return this;
    }


    public StorageRestCall get() {
        return this.setHttpMethod(HttpMethod.GET);
    }

    public StorageRestCall post() {
        return this.setHttpMethod(HttpMethod.POST);
    }

    public StorageRestCall put() {
        return this.setHttpMethod(HttpMethod.PUT);
    }

    public StorageRestCall delete() {
        return this.setHttpMethod(HttpMethod.DELETE);
    }

    static public StorageRestCall post(String server) {
        StorageRestCall result = new StorageRestCall(server);
        return result.setHttpMethod(HttpMethod.POST);
    }

    static public StorageRestCall put(String server) {
        StorageRestCall result = new StorageRestCall(server);
        return result.setHttpMethod(HttpMethod.PUT);
    }

    static public StorageRestCall get(String server) {
        StorageRestCall result = new StorageRestCall(server);
        return result.setHttpMethod(HttpMethod.GET);
    }

    static public StorageRestCall delete(String server) {
        StorageRestCall result = new StorageRestCall(server);
        return result.setHttpMethod(HttpMethod.DELETE);
    }

    static public StorageRestCall instance() {
        StorageRestCall result = new StorageRestCall();
        return result;
    }



    static public void main(String[] args ) throws Exception {
        // POST Create
        // PUT Update
        Storage user = new Storage();
        user.setDescription("Big fucking Gun");
        StorageRestCall storageRestCall = StorageRestCall
                .instance()
                .setServer("http://localhost:8089")
                .post()
                .setWebContext("/workflow/storage/file/Catálogos/Usuarios/BFG4000")
                .setHeader("Authorization","token VPqM7akss8ifIkjz0bA0eyHNo3N8PAkH02iKoEZ48sIkwxBevF+Wjddr7zgfx1H9ji8+wmZAI8v5p2z94fHyW6sKpuGfBq6GjR7aV2QKP5Dd5XKhSxpWeMoz6F8/z/NFBQv4GUO4drWE9eTXHNQI9/1pX7f5xsXXOU/eRYwts5k7XVhc4PjV8YuJVuC820f5W+90RTufkoWdu8aHba1t/g==")
                .setBody(user)
                .send();

        if( storageRestCall.getResponse() != null  ) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectResponse<Storage> res =
                    mapper.readValue(storageRestCall.getResponse(), new TypeReference<ObjectResponse<Storage>>() {
                    });

            System.out.println(res);
        }


    }
}
