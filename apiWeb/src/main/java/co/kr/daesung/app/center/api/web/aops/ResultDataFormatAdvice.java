package co.kr.daesung.app.center.api.web.aops;

import co.kr.daesung.app.center.api.web.vos.ResultData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 2:42 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
@Aspect
public class ResultDataFormatAdvice implements Ordered {
    @Pointcut(value = "@annotation(co.kr.daesung.app.center.api.web.aops.ResultDataFormat)")
    private void resultDataFormatPointcut() {
    }

    @Around("resultDataFormatPointcut()")
    public Object wrapResponseObject(ProceedingJoinPoint pjp) throws Throwable {
        try {
            Object ret = pjp.proceed();
            return new ResultData(ret);
        } catch (Exception ex) {
            return new ResultData(ex);
        }
    }

    @Override
    public int getOrder() {
        return 2;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
