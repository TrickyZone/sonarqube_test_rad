package com.knoldus.radarservice.config;

import com.knoldus.radarservice.config.db.DbConfig;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * General configuration of the application.
 *
 * @author Romit Saxena
 */
@ComponentScan(basePackages = {
        "com.knoldus.radarservice",
        "io.swagger.client",
        "springfox.collection.example.plugins"
})
@Import({
        DbConfig.class,
        SwaggerConfig.class,
        WebConfig.class
})
public class AppConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        // We use both default spring messages and our own, in order to pick up the default validation error messages
        messageSource.setBasenames("classpath:messages", "classpath:/i18n/messages");

        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
}
