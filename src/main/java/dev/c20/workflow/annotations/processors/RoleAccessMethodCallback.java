package dev.c20.workflow.annotations.processors;

import dev.c20.workflow.annotations.Role;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class RoleAccessMethodCallback implements ReflectionUtils.MethodCallback {

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

    private ConfigurableListableBeanFactory configurableBeanFactory;
    private Object bean;

    public RoleAccessMethodCallback(ConfigurableListableBeanFactory bf, Object bean) {
        configurableBeanFactory = bf;
        this.bean = bean;
    }
    @Override
    public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {

        ReflectionUtils.makeAccessible(method);

        Role classValue = method.getDeclaredAnnotation(Role.class);
        if( classValue == null ) {
            String[] groups = classValue.groups();

            logger.info("Method:" + method.getName() + " Groups:" + groups);
        }

    }
}
