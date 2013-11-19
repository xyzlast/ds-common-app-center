package co.kr.daesung.app.center.api.web.auth;

import co.kr.daesung.app.center.domain.entities.auth.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public interface UserInfoHelper {
    User getUserFromRequest(HttpServletRequest request);
}
