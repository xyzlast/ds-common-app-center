package co.kr.daesung.app.center.domain.configs;

import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Date;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/4/13
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@EnableJpaRepositories(basePackages = "co.kr.daesung.app.center.domain.repo")
@EnableTransactionManagement
@PropertySource(value = "classpath:db-connect.properties")
@ComponentScan(basePackages = "co.kr.daesung.app.center.domain.services")
public class DomainConfiguration {

    public static final String HIBERNATE_DIALECT = "hibernate.dialect";
    public static final String CONNECT_USERNAME = "connect.username";
    public static final String CONNECT_PASSWORD = "connect.password";
    public static final String CONNECT_DRIVER = "connect.driver";
    public static final String CONNECT_URL = "connect.url";

    public static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    public static final String EH_CACHE_REGION_FACTORY = "org.hibernate.cache.ehcache.EhCacheRegionFactory";
    public static final String HIBERNATE_CACHE_USE_QUERY_CACHE = "hibernate.cache.use_query_cache";
    public static final String HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = "hibernate.cache.use_second_level_cache";
    public static final String HIBERNATE_CACHE_REGION_FACTORY_CLASS = "hibernate.cache.region.factory_class";
    public static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

    @Autowired
    private Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configHolder = new PropertySourcesPlaceholderConfigurer();
        return configHolder;
    }

    @Bean(name = "ehcache")
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() throws Exception {
        EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
        bean.setShared(Boolean.TRUE);
        bean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        bean.afterPropertiesSet();

        return bean;
    }

    @Bean(name = "cacheManager")
    public EhCacheCacheManager cacheManager() throws Exception {
        EhCacheCacheManager ehCacheManager = new EhCacheCacheManager();
        ehCacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());
        return ehCacheManager;
    }

    @Bean
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setUsername(env.getProperty(CONNECT_USERNAME));
        dataSource.setPassword(env.getProperty(CONNECT_PASSWORD));
        dataSource.setDriverClass(env.getProperty(CONNECT_DRIVER));
        dataSource.setJdbcUrl(env.getProperty(CONNECT_URL));
        dataSource.setMaxConnectionsPerPartition(10);
        dataSource.setMinConnectionsPerPartition(5);

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource());
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPackagesToScan("co.kr.daesung.app.center.domain.entities");
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter());
        entityManagerFactory.setJpaProperties(getHibernateProperties());
        return entityManagerFactory;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        return hibernateJpaVendorAdapter;
    }

    protected Properties getHibernateProperties() {
        Properties properties = new Properties();
        Assert.notNull(env);
        properties.put(HIBERNATE_DIALECT, env.getProperty(HIBERNATE_DIALECT));
        properties.put(HIBERNATE_CACHE_REGION_FACTORY_CLASS, EH_CACHE_REGION_FACTORY);
        properties.put(HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE, true);
        properties.put(HIBERNATE_CACHE_USE_QUERY_CACHE, true);
        properties.put("net.sf.ehcache.cacheManagerName", Long.valueOf((new Date()).getTime()).toString());

        return properties;
    }

}
