package org.itransition.taskmanager.config.databases;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Primary
    @Bean
    @ConfigurationProperties("database.relational.migrations")
    public LiquibaseProperties liquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Primary
    @Bean
    public SpringLiquibase springLiquibase(@Qualifier("dataSource") DataSource dataSource,
                                           @Qualifier("liquibaseProperties") LiquibaseProperties liquibaseProperties) {

        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(dataSource);
        springLiquibase.setChangeLog(liquibaseProperties.getChangeLog());
        springLiquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        return springLiquibase;
    }
}
