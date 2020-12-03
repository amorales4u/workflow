package dev.c20.workflow.annotations.processors;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.auth.entities.UserEntity;
import dev.c20.workflow.storage.entities.adds.Data;
import dev.c20.workflow.tools.PathUtils;
import dev.c20.workflow.tools.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class RoleAnnotationProcessor  {

    protected final Log logger = LogFactory.getLog(this.getClass());


    public boolean authorize(HttpServletRequest request) {

        UserEntity userEntity = UserEntity.fromToken(request.getHeader(WorkflowApplication.HEADER_AUTHORIZATION));

        return true;
    }
}
