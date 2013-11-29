package co.kr.daesung.app.center.api.web.cors;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/29/13
 * Time: 10:16 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CorsHeaderApplierImpl implements CorsHeaderApplier {
    @Override
    public void applyCorsHeaders(HttpServletRequest request, HttpServletResponse response) {
        String origin = request.getHeader("Origin");
        response.addHeader("Access-Control-Allow-Origin", origin);
        response.addHeader("Access-Control-Allow-Methods", "GET, DELETE, POST, PUT, OPTIONS, HEAD");
        response.addHeader("Access-Control-Max-Age", "0");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Allow-Credentials", "true");
    }
}
