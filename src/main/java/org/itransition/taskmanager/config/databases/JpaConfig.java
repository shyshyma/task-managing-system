package org.itransition.taskmanager.config.databases;

import org.springframework.beans.factory.annotation.Qualifier;
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
        basePackages = {"org.itransition.taskmanager.repositories.jpa"})
public class JpaConfig {

    @Bean
    @ConfigurationProperties("database.relational.orm.jpa")
    public Map<String, String> jpaProperties() {
        return new HashMap<>();
    }


    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("dataSource") DataSource dataSource,
            @Qualifier("jpaProperties") Map<String, ?> properties) {

        return builder.dataSource(dataSource)
                .packages("org.itransition.taskmanager.models.jpa")
                .persistenceUnit("PRIMARY_JPA_DATABASE")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager jpaTransactionManager(@Qualifier("jpaEntityManagerFactory")
                                                                    EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}