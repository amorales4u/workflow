package dev.c20.workflow.commons.wrapper;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.commons.tools.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RestCall {

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

    static public HttpServletRequest getHttpServletRequest() {
        HttpServletRequest httpRequest =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();

        return httpRequest;
    }

    static public String getThisServerURI() {
        HttpServletRequest httpRequest = getHttpServletRequest();

        return httpRequest.getScheme() +
                "://" + httpRequest.getServerName() +
                ":" + httpRequest.getLocalPort()
                ;

    }

    public Object post(String webContext, Object body) {

        try {

            logger.info("Sending message to:" + webContext);

            HttpServletRequest httpRequest = getHttpServletRequest();

            webContext =  getThisServerURI() + webContext;

            int timeout = 1000;
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout)
                    .setSocketTimeout(timeout).build();

            CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

            HttpPost httpPost = new HttpPost(webContext);

            StringEntity entity = new StringEntity(StringUtils.toJSON(body));
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader(WorkflowApplication.HEADER_AUTHORIZATION, httpRequest.getHeader(WorkflowApplication.HEADER_AUTHORIZATION));

            CloseableHttpResponse response = client.execute(httpPost);
            Object result = StringUtils.fromJSON(response.getEntity().getContent().toString());
            client.close();
            logger.info("Sended message to:" + webContext);
            return result;
        } catch (Exception ex) {
            logger.error(ex);
        }

        return null;
    }


}
