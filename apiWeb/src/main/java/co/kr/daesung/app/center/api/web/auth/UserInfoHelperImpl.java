package co.kr.daesung.app.center.api.web.auth;

import co.kr.daesung.app.center.domain.entities.auth.User;
import co.kr.daesung.app.center.domain.services.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.codec.Base64;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class UserInfoHelperImpl implements UserInfoHelper {
    public static final String AUTH_HEADER = "Authorization";

    private UserService userService;

    @Override
    public User getUserFromRequest(HttpServletRequest request) {
        String authHeaderValue = request.getHeader(AUTH_HEADER);
        String authValue = authHeaderValue.substring(6);
        try {
            String decodedValue = new String(Base64.decode(authValue.getBytes("utf-8")), "utf-8");
            String[] items = decodedValue.split(":");
            String username = items[0];
            return userService.findByUsername(username);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Request has no Authorization information");
        }
    }
}
