package com.knoldus.radarservice.config.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import java.nio.file.Paths;
import java.util.HashMap;
import javax.sql.DataSource;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.core.env.Environment;

class LiquibaseMigrationConfigTest {
    /**
     * Method under test: {@link LiquibaseMigrationConfig#liquibase(DataSource, LiquibaseProperties, Environment)}
     */
    @Test
    void testLiquibase() {
        LiquibaseMigrationConfig liquibaseMigrationConfig = new LiquibaseMigrationConfig();
        DataSource dataSource = mock(DataSource.class);

        LiquibaseProperties liquibaseProperties = new LiquibaseProperties();
        liquibaseProperties.setChangeLog("Change Log");
        liquibaseProperties.setClearChecksums(true);
        liquibaseProperties.setContexts("Contexts");
        liquibaseProperties.setDatabaseChangeLogLockTable("Database Change Log Lock Table");
        liquibaseProperties.setDatabaseChangeLogTable("Database Change Log Table");
        liquibaseProperties.setDefaultSchema("Default Schema");
        liquibaseProperties.setDriverClassName("Driver Class Name");
        liquibaseProperties.setDropFirst(true);
        liquibaseProperties.setEnabled(true);
        liquibaseProperties.setLabels("Labels");
        liquibaseProperties.setLiquibaseSchema("Liquibase Schema");
        liquibaseProperties.setLiquibaseTablespace("Liquibase Tablespace");
        liquibaseProperties.setParameters(new HashMap<>());
        liquibaseProperties.setPassword("iloveyou");
        liquibaseProperties.setRollbackFile(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());
        liquibaseProperties.setTag("Tag");
        liquibaseProperties.setTestRollbackOnUpdate(true);
        liquibaseProperties.setUrl("https://example.org/example");
        liquibaseProperties.setUser("User");
        assertFalse(
                liquibaseMigrationConfig.liquibase(dataSource, liquibaseProperties, new StandardReactiveWebEnvironment())
                        .isTestRollbackOnUpdate());
    }

    /**
     * Method under test: {@link LiquibaseMigrationConfig#liquibase(DataSource, LiquibaseProperties, Environment)}
     */
    @Test
    void testLiquibase2() {
        LiquibaseMigrationConfig liquibaseMigrationConfig = new LiquibaseMigrationConfig();
        DataSource dataSource = mock(DataSource.class);

        LiquibaseProperties liquibaseProperties = new LiquibaseProperties();
        liquibaseProperties.setChangeLog("Change Log");
        liquibaseProperties.setClearChecksums(true);
        liquibaseProperties.setContexts("Contexts");
        liquibaseProperties.setDatabaseChangeLogLockTable("Database Change Log Lock Table");
        liquibaseProperties.setDatabaseChangeLogTable("Database Change Log Table");
        liquibaseProperties.setDefaultSchema("Default Schema");
        liquibaseProperties.setDriverClassName("Driver Class Name");
        liquibaseProperties.setDropFirst(true);
        liquibaseProperties.setLabels("Labels");
        liquibaseProperties.setLiquibaseSchema("Liquibase Schema");
        liquibaseProperties.setLiquibaseTablespace("Liquibase Tablespace");
        liquibaseProperties.setParameters(new HashMap<>());
        liquibaseProperties.setPassword("iloveyou");
        liquibaseProperties.setRollbackFile(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());
        liquibaseProperties.setTag("Tag");
        liquibaseProperties.setTestRollbackOnUpdate(true);
        liquibaseProperties.setUrl("https://example.org/example");
        liquibaseProperties.setUser("User");
        liquibaseProperties.setEnabled(false);
        assertFalse(
                liquibaseMigrationConfig.liquibase(dataSource, liquibaseProperties, new StandardReactiveWebEnvironment())
                        .isTestRollbackOnUpdate());
    }
}

