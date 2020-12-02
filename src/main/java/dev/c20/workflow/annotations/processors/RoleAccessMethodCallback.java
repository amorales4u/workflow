package dev.c20.workflow.annotations.processors;

import dev.c20.workflow.annotations.Group;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class RoleAccessMethodCallback implements ReflectionUtils.MethodCallback {

    private ConfigurableListableBeanFactory configurableBeanFactory;
    private Object bean;

    public RoleAccessMethodCallback(ConfigurableListableBeanFactory bf, Object bean) {
        configurableBeanFactory = bf;
        this.bean = bean;
    }
    @Override
    public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {

        ReflectionUtils.makeAccessible(method);

        Group classValue = method.getDeclaredAnnotation(Group.class);
    }
}
