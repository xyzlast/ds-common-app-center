package co.kr.daesung.app.center.api.web.cors;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/21/13
 * Time: 12:57 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CorsSupportLoginUrlAuthenticationEntryPoint
        extends DigestAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    public CorsSupportLoginUrlAuthenticationEntryPoint() {
        super.setRealmName("ykyoon");
        super.setKey("xyzlast");
        super.setNonceValiditySeconds(30);
    }

    @Autowired
    private CorsHeaderApplier corsHeaderApplier;

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException, ServletException {
        if(isPreflight(request)){
            corsHeaderApplier.applyCorsHeaders(request, response);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else  {
            corsHeaderApplier.applyCorsHeaders(request, response);
            super.commence(request, response, authException);
        }
    }

    /**
     * Checks if this is a X-domain pre-flight request.
     * @param request
     * @return
     */
    private boolean isPreflight(HttpServletRequest request) {
        return request.getMethod().equals("OPTIONS");
    }
}