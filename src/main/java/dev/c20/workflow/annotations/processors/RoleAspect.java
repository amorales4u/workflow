package dev.c20.workflow.annotations.processors;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.annotations.Roles;
import dev.c20.workflow.auth.entities.UserEntity;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class RoleAspect {

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());
/*
    @Autowired
    RoleAnnotationProcessor roleAnnotationProcessor;
*/

    @Before("@annotation(dev.c20.workflow.annotations.Roles) && args(request,..)")
    public void before( JoinPoint jp, HttpServletRequest request){
        /*
        if (!(request instanceof HttpServletRequest)) {
            throw
                    new RuntimeException("request should be HttpServletRequesttype");
        }
*/
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        Roles[] roles = method.getAnnotationsByType(Roles.class);

        UserEntity userEntity = UserEntity.fromToken(request.getHeader(WorkflowApplication.HEADER_AUTHORIZATION));

        if( userEntity == null ) {
            throw  new RuntimeException("No esta firmado el usuario");
        }

        boolean hasRole = false;
        String rolesStr = "";
        for( Roles role : roles ) {

            for( String roleStr : role.value() ) {
                logger.info("Role " + roleStr);
                rolesStr += roleStr + ",";
                hasRole = userEntity.getRoles().contains(roleStr);
                if( hasRole ) {
                    break;
                }
            }
        }

        if( hasRole ) {
            return;
        } else {
            throw  new RuntimeException("El usuario No tiene permiso para el algún Rol " + rolesStr);
        }


    }

    public void myadvice(JoinPoint jp) {
        logger.info("MyAdvise");
        logger.info(jp);
    }

}
