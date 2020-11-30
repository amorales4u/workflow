package dev.c20.workflow.filters;

import com.sun.corba.se.impl.presentation.rmi.DynamicMethodMarshallerImpl;
import org.apache.commons.io.IOUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "ContentCachingFilter", urlPatterns = "/*")
public class RequestFilter  extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("IN  ContentCachingFilter ");
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpServletRequest);
        String body = IOUtils.toString(cachedBodyHttpServletRequest.getReader());
        body.replaceAll("(?i)<(/?script[^>]*)>", "&lt;$1&gt;");
        System.err.println("Cache Request:" + IOUtils.toString(cachedBodyHttpServletRequest.getReader()));
        filterChain.doFilter(cachedBodyHttpServletRequest, httpServletResponse);

    }
}
