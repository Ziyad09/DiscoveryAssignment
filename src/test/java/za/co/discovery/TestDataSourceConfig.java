package za.co.discovery;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class TestDataSourceConfig {

    @Bean
    @FlywayDataSource
    @Profile("test")
    public DataSource dataSourceTest() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:mem:PUBLIC;sql.syntax_pgs=true;sql.syntax_ora=true");

        return dataSource;
    }

    @Bean
    @Profile("test")
    @Qualifier("hibernateProperties")
    public Properties hsqlHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");

        return properties;
    }

}
