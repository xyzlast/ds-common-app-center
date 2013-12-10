package co.kr.daesung.app.center.api.web.configs;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 1:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class WebXmlInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { DomainConfiguration.class, SecurityConfiguration.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { ControllerConfiguration.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ServletRegistration.Dynamic cxf = servletContext.addServlet("CXFServlet", CXFServlet.class);
        cxf.addMapping("/ws/*");
        cxf.setLoadOnStartup(1);
        super.onStartup(servletContext);
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");

        DelegatingFilterProxy delegatingFilterProxy =
                new DelegatingFilterProxy(SecurityConfiguration.CORS_SUPPORT_FILTER);

        return new Filter[] { delegatingFilterProxy, characterEncodingFilter };
    }
}
