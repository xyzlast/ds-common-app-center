package co.kr.daesung.app.center.domain.configs;

import com.jolbox.bonecp.BoneCPDataSource;
import me.xyzlast.configs.EhCacheConfigurer;
import me.xyzlast.configs.EnableOrm;
import me.xyzlast.configs.HbmToDdl;
import me.xyzlast.configs.OrmFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
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
@PropertySource(value = "classpath:db-connect.properties")
@ComponentScan(basePackages = { "co.kr.daesung.app.center.domain.services", "co.kr.daesung.app.center.domain.repo" })
@EnableOrm(enableCache = false,
        framework = OrmFramework.JPA,
        hbmToDdl = HbmToDdl.NO_ACTION,
        packagesToScan = {"co.kr.daesung.app.center.domain.entities"},
        showSql = false)
// @Import(EhCacheConfigurer.class)
@EnableCaching(order = 0)
public class DomainConfiguration extends EhCacheConfigurer {
    @Autowired
    private Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configHolder = new PropertySourcesPlaceholderConfigurer();
        Properties properties = new Properties();
        properties.setProperty("org.jboss.logging.provier", "slf4j");
        configHolder.setProperties(properties);
        return configHolder;
    }

    @Bean
    public JdbcTemplate nleaderJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(nleaderDataSource());
        return jdbcTemplate;
    }

    @Bean(name = "nleaderDataSource")
    public DataSource nleaderDataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setUsername(env.getProperty("message.nleader.username"));
        dataSource.setPassword(env.getProperty("message.nleader.password"));
        dataSource.setDriverClass(env.getProperty("com.microsoft.sqlserver.jdbc.SQLServerDriver"));
        dataSource.setJdbcUrl(env.getProperty("message.nleader.url"));
        dataSource.setMaxConnectionsPerPartition(10);
        dataSource.setMinConnectionsPerPartition(5);

        return dataSource;
    }
}
