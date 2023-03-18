package com.knoldus.radarservice.config.db;

import org.springframework.context.annotation.Import;

/**
 * General configuration for the database.
 *
 * @author Romit Saxena
 */
@Import({LiquibaseMigrationConfig.class})
public class DbConfig {

}
