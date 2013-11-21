package co.kr.daesung.app.center.api.web.configs;

import co.kr.daesung.app.center.api.web.filters.OptionsHeadersFilter;
import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

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
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        OptionsHeadersFilter optionsHeadersFilter = new OptionsHeadersFilter();
        return new Filter[] { optionsHeadersFilter, characterEncodingFilter };
    }
}
