package co.kr.daesung.app.center.api.web.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.ELRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/21/13
 * Time: 12:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class AjaxAwareLoginUrlAuthenticationEntryPoint extends
        LoginUrlAuthenticationEntryPoint {

    private static final RequestMatcher requestMatcher = new ELRequestMatcher(
            "hasHeader('X-Requested-With','XMLHttpRequest')");

    @SuppressWarnings("deprecation")
    public AjaxAwareLoginUrlAuthenticationEntryPoint() {
        super();
    }

    public AjaxAwareLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException, ServletException {

        response.addHeader("Access-Control-Allow-Origin", "http://localhost:9000");
        response.addHeader("Access-Control-Allow-Methods", "DELETE, PUT, GET, POST, OPTION");
        response.addHeader("Access-Control-Max-Age", "1000");
        response.addHeader("Access-Control-Allow-Credentials", "true");

        if(isPreflight(request)){
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else if (isRestRequest(request)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        } else {
            super.commence(request, response, authException);
        }
    }

    /**
     * Checks if this is a X-domain pre-flight request.
     * @param request
     * @return
     */
    private boolean isPreflight(HttpServletRequest request) {
        return "OPTIONS".equals(request.getMethod());
    }

    /**
     * Checks if it is a rest request
     * @param request
     * @return
     */
    protected boolean isRestRequest(HttpServletRequest request) {
        return requestMatcher.matches(request);
    }
}