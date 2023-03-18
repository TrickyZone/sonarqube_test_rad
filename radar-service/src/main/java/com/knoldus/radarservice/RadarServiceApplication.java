package com.knoldus.radarservice;

import com.knoldus.radarservice.config.AppConfig;
import com.knoldus.radarservice.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * General configuration of the application.
 *
 * @author Romit Saxena
 */
@SpringBootApplication
@Import({AppConfig.class, WebConfig.class})

public class RadarServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RadarServiceApplication.class, args);
	}

}
