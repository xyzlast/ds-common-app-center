package co.kr.daesung.app.center.api.web.aops;

import co.kr.daesung.app.center.domain.services.ApiKeyService;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 8:41 AM
 * To change this template use File | Settings | File Templates.
 */
@Aspect
@Component
public class AuthKeyCheckAdvice implements Ordered {
    @Autowired
    private ApiKeyService service;

    public static final String NOT_AUTHORIZED = "This key is not authorized!";

    @Pointcut(value = "@annotation(co.kr.daesung.app.center.api.web.aops.AuthKeyCheck)")
    private void checkKeyPointcut() {
    }

    @Around("checkKeyPointcut()")
    public Object checkKey(ProceedingJoinPoint pjp) throws Throwable {
        Object[] arguments = pjp.getArgs();

        if(arguments.length >= 2 && arguments[0] instanceof HttpServletRequest &&
            arguments[1] instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) arguments[0];
            String headerClient = request.getHeader("client");
            if(headerClient == null || headerClient.equals("")) {
                headerClient = request.getHeader("Referer");
            }

            final String[] keys = request.getParameterValues("key");
            if(keys != null && keys.length == 1) {
                String key = keys[0];
                if(headerClient != null && !headerClient.equals("")) {
                    String patternString = "(?<httpUrl>((https?:\\/\\/)([\\da-z\\.-]+)))\\/?";
                    Pattern pattern = Pattern.compile(patternString);
                    final Matcher matcher = pattern.matcher(headerClient);
                    if(matcher.find()) {
                        headerClient = matcher.group("httpUrl");
                    }
                    if(service.isAcceptedKey(key, headerClient)) {
                        return pjp.proceed();
                    }
                }
            }
        }
        throw new IllegalArgumentException(NOT_AUTHORIZED);
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
