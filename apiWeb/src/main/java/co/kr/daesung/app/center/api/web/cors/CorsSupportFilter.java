package co.kr.daesung.app.center.api.web.cors;

import co.kr.daesung.app.center.api.web.configs.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/21/13
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
@Component(value = SecurityConfiguration.CORS_SUPPORT_FILTER)
public class CorsSupportFilter implements Filter {
    @Autowired
    private CorsHeaderApplier corsHeaderApplier;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        corsHeaderApplier.applyCorsHeaders(request, response);
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
