package co.kr.daesung.app.center.api.web.aops;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/20/13
 * Time: 10:54 AM
 * To change this template use File | Settings | File Templates.
 */
@Aspect
@Component
public class AllowCrossDomainAdvice {
    @Pointcut(value = "@annotation(co.kr.daesung.app.center.api.web.aops.AllowCrossDomain)")
    private void allowCrossDomainPointcut() {

    }

    @After(value = "allowCrossDomainPointcut()")
    public void setCrossDomainResponseHeader(JoinPoint thisJoinPoint) {
        Object[] arguments = thisJoinPoint.getArgs();
        if(arguments.length >= 2 && arguments[0] instanceof HttpServletRequest &&
                arguments[1] instanceof HttpServletResponse) {
            HttpServletResponse response = (HttpServletResponse) arguments[1];
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "'DELETE', 'PUT', 'GET', 'POST'");
            response.addHeader("Access-Control-Max-Age", "1000");
        }
    }
}
