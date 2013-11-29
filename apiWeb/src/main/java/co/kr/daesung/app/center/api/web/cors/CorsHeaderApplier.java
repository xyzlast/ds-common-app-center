package co.kr.daesung.app.center.api.web.cors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/29/13
 * Time: 10:14 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CorsHeaderApplier {
    void applyCorsHeaders(HttpServletRequest request, HttpServletResponse response);
}
