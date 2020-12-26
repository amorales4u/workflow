package dev.c20.workflow.flow.services;

import dev.c20.workflow.commons.tools.WFException;
import dev.c20.workflow.flow.responses.EvalResult;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.MissingPropertyException;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EvalProcessService {

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

    private String code = null;

    public String getCode() { return this.code; };

    public EvalProcessService setCode(String code) {
        this.code = code;
        return this;
    }

    @SuppressWarnings("unchecked")
    protected EvalResult executeCode(Map<String,Object> context, String nameCode, String methodName ) throws WFException {

        EvalResult result = new EvalResult();

        final org.apache.commons.logging.Log serviceLogger = LogFactory.getLog(nameCode);

        try {
            ((Map<String,Object>)context.get("params")).put("serviceName", nameCode);
            ClassLoader parent = getClass().getClassLoader();
            GroovyClassLoader loader = new GroovyClassLoader(parent);
            @SuppressWarnings("rawtypes")
            Class groovyClass = loader.parseClass(code);

            // let's call some method on an instance
            GroovyObject groovyObject = (GroovyObject) groovyClass .newInstance();
            String argsNames[] = { "context" };
            Object args[] = { context };

            for (int i = 0; i < argsNames.length; i++)
                groovyObject.setProperty(argsNames[i], args[i]);

            logger.debug("executeCode ==> Invocando el servicio");


            Object response = groovyObject.invokeMethod(methodName, null);

            logger.info("executeCode " + nameCode + " ==> Ejecuci�n del Service : " + result);
            loader.close();
            result.setResponse(response);
            return result;

        } catch( MultipleCompilationErrorsException compile ) {
            serviceLogger.error("MultipleCompilationErrorsException",compile);
            logger.error("Script:" + code);
            result.setErrorMessage("Error de compilación: " + nameCode + " " + compile.getMessage() );
            throw new WFException("TaskService:executeCode " + nameCode + " " + result.getErrorMessage() );
        } catch( MissingPropertyException compile ) {
            serviceLogger.error("MissingPropertyException",compile);
            result.setErrorMessage( "Error de compilación " + nameCode + " " + ": No existe la propiedad " + compile.getMessage() );
            throw new WFException("TaskService:executeCode " + nameCode + " " +  result.getErrorMessage() );
        } catch( GroovyRuntimeException compile ) {
            serviceLogger.error("RuntimeException", compile);
            result.setErrorMessage( "Excepción :  " + nameCode + " " +  compile.getMessage() );
            throw new WFException("TaskService:executeCode " + nameCode + " " +  result.getErrorMessage()  );
        } catch (Exception ex) {
            logger.error("*** TaskService.executeService ==> Error al ejecutar el Servicio "+ nameCode + " " );
            serviceLogger.error("Exception", ex);
            result.setErrorMessage( ex.getMessage());
            throw new WFException("TaskService:executeCode " + result.getErrorMessage() );
        }

    }


}
