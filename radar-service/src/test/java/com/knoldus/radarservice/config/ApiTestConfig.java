package com.knoldus.radarservice.config;

import org.springframework.context.annotation.Import;

/**
 * Configuration for API tests.
 */
@Import({AppConfig.class})
public class ApiTestConfig {

}
