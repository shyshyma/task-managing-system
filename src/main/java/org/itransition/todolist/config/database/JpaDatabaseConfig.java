package org.itransition.todolist.config.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(transactionManagerRef = "jpaDatabaseTransactionManager",
        entityManagerFactoryRef = "jpaEntityManagerFactory",
        basePackages = {"org.itransition.todolist.repositories.jpa"})
public class JpaDatabaseConfig {


    @Primary
    @Bean("jpaDatabaseProperties")
    @ConfigurationProperties("database.jpa")
    public DataSourceProperties jpaDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean("jpaDataSource")
    public DataSource jpaDataSource(@Qualifier("jpaDatabaseProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }


    @Bean("jpaDatabaseConfiguration")
    @ConfigurationProperties("database.jpa.configuration")
    public Map<String, String> jpaDataSourceConfiguration() {
        return new HashMap<>();
    }


    @Primary
    @Bean(name = "jpaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("jpaDataSource") DataSource dataSource,
            @Qualifier("jpaDatabaseConfiguration") Map<String, ?> properties) {

        return builder.dataSource(dataSource)
                .packages("org.itransition.todolist.models.jpa")
                .persistenceUnit("JPA_DATABASE")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "jpaDatabaseTransactionManager")
    public PlatformTransactionManager jpaTransactionManager(@Qualifier("jpaEntityManagerFactory")
                                                                    EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }

}