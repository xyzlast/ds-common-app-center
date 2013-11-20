package co.kr.daesung.app.center.api.web.aops;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 2:51 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
@Aspect
public class JsonpAdvice implements Ordered {
    @Autowired
    private ObjectMapper objectMapper;

    @Pointcut(value = "@annotation(co.kr.daesung.app.center.api.web.aops.Jsonp)")
    private void jsonpPointcut() {

    }

    @Around("jsonpPointcut()")
    public Object applyJsonp(ProceedingJoinPoint pjp) throws Throwable {
        Object[] arguments = pjp.getArgs();
        Object result = pjp.proceed();
        if(arguments.length < 2) {
            return result;
        } else {
            if(arguments[0] instanceof HttpServletRequest &&
               arguments[1] instanceof HttpServletResponse) {
                HttpServletRequest request = (HttpServletRequest) arguments[0];
                String[] callbackMethods = request.getParameterValues("callback");
                if(callbackMethods == null || callbackMethods.length != 1) {
                    return result;
                }
                String callbackMethod = callbackMethods[0];

                HttpServletResponse response = (HttpServletResponse) arguments[1];
                response.setContentType("application/javascript");
                response.setCharacterEncoding("utf-8");

                PrintWriter out = response.getWriter();
                out.print(String.format("%s(%s)", callbackMethod,
                        objectMapper.writeValueAsString(result)));
                return null;
            } else {
                return result;
            }
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
