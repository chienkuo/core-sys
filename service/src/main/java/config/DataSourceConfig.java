package config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Akuo on 2017/6/5.
 */
@Configuration
@ComponentScan(basePackages = "me.akuo")
@PropertySource(value = "classpath:jdbc.properties")
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages = "me.akuo.mapper")
public class DataSourceConfig {
    private static final Logger logger = LoggerFactory
            .getLogger(DataSourceConfig.class);

    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.user}")
    private String user;
    @Value("${jdbc.password}")
    private String password;

    @Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource createDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(this.url);
        druidDataSource.setUsername(this.user);
        druidDataSource.setPassword(this.password);
        druidDataSource.setInitialSize(1);
        druidDataSource.setMinIdle(1);
        druidDataSource.setMaxActive(1);
        druidDataSource.setMaxWait(60000L);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000L);
        druidDataSource.setMinEvictableIdleTimeMillis(300000L);
        druidDataSource.setValidationQuery("SELECT 'x' FROM DUAL");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setPoolPreparedStatements(true);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        druidDataSource.setFilters("stat");
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource, SpringManagedTransactionFactory springManagedTransactionFactory)
            throws IOException {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        Properties properties = new Properties();
        properties.setProperty("lazyLoadingEnabled", "false");
        sessionFactory.setConfigurationProperties(properties);
        sessionFactory.setTransactionFactory(springManagedTransactionFactory);
        sessionFactory.setTypeAliasesPackage("me.akuo.domain");
        //sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return sessionFactory;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(
            DataSource dataSource) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        manager.setDefaultTimeout(30);
        return manager;
    }

    @Bean
    public SpringManagedTransactionFactory springManagedTransactionFactory() {
        return new SpringManagedTransactionFactory();
    }

}
