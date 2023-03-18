package com.knoldus.radarservice.config.db;

import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import javax.sql.DataSource;

/**
 * Configuration for the database migrations.
 *
 * @author Romit Saxena
 */
@EnableConfigurationProperties({LiquibaseProperties.class})
class LiquibaseMigrationConfig {

    private static boolean migrationsHaveRun = false;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public SpringLiquibase liquibase(
            DataSource dataSource,
            LiquibaseProperties liquibaseProperties,
            Environment springEnvironment) {

        boolean skipMigrations = springEnvironment.acceptsProfiles(Profiles.of("fast"));
        boolean runMigrations = !skipMigrations && !migrationsHaveRun && liquibaseProperties.isEnabled();
        SpringLiquibase liquibase = new SpringLiquibase();

        if (runMigrations) {
            liquibase.setShouldRun(true);
            liquibase.setDataSource(dataSource);
            liquibase.setChangeLog("classpath:liquibase/master.xml");
            liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
            liquibase.setDropFirst(liquibaseProperties.isDropFirst());

            logger.info("Migrations will run.");
        } else {
            liquibase.setShouldRun(false);
            logger.info("Migrations will not run.");
        }

        migrationsHaveRun = true;

        return liquibase;
    }
}
