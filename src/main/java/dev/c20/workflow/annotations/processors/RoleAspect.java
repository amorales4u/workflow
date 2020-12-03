package dev.c20.workflow.annotations.processors;

import dev.c20.workflow.annotations.Roles;
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
    @Autowired
    HttpServletRequest req;

    @Before("@annotation(dev.c20.workflow.annotations.Roles) && args(request,..)")
    public void before( JoinPoint jp, HttpServletRequest request){
        /*
        if (!(request instanceof HttpServletRequest)) {
            throw
                    new RuntimeException("request should be HttpServletRequesttype");
        }
*/
        logger.info("In Aspect before for Role" + jp);
        logger.info("In Aspect before for Role" + request);
        logger.info("In Aspect before for Role Autowired " + req.getRequestURI());
        logger.info("Has Roles: " + jp.getSignature().getName() );

        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        Roles[] roles = method.getAnnotationsByType(Roles.class);

        for( Roles role : roles ) {

            for( String roleStr : role.value() )
                logger.info("Role " + roleStr);
        }


        //logger.info(joinPoint.getArgs());
/*
        if(roleAnnotationProcessor.authorize(request)){
            logger.info(joinPoint.getArgs());
            request.setAttribute(
                    "userSession",
                    "session information which cann be acces in controller"
            );
        }else {
            throw new RuntimeException("auth error..!!!");
        }
*/

    }

    public void myadvice(JoinPoint jp) {
        logger.info("MyAdvise");
        logger.info(jp);
    }

}
