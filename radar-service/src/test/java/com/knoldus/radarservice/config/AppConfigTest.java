package com.knoldus.radarservice.config;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

class AppConfigTest {
    /**
     * Method under test: {@link AppConfig#messageSource()}
     */
    @Test
    void testMessageSource() {
        assertTrue((new AppConfig()).messageSource() instanceof ReloadableResourceBundleMessageSource);
    }

    /**
     * Method under test: {@link AppConfig#validatorFactoryBean(MessageSource)}
     */
    @Test
    void testValidatorFactoryBean() {
        AppConfig appConfig = new AppConfig();
        assertTrue(appConfig.validatorFactoryBean(new AnnotationConfigReactiveWebApplicationContext())
                .getValidationPropertyMap()
                .isEmpty());
    }

    /**
     * Method under test: {@link AppConfig#validatorFactoryBean(MessageSource)}
     */
    @Test
    void testValidatorFactoryBean2() {
        AppConfig appConfig = new AppConfig();

        DelegatingMessageSource delegatingMessageSource = new DelegatingMessageSource();
        delegatingMessageSource.setAlwaysUseMessageFormat(true);
        delegatingMessageSource.setParentMessageSource(new AnnotationConfigReactiveWebApplicationContext());
        assertTrue(appConfig.validatorFactoryBean(delegatingMessageSource).getValidationPropertyMap().isEmpty());
    }

    /**
     * Method under test: {@link AppConfig#validatorFactoryBean(MessageSource)}
     */
    @Test
    void testValidatorFactoryBean4() {
        AppConfig appConfig = new AppConfig();

        AnnotationConfigReactiveWebApplicationContext annotationConfigReactiveWebApplicationContext = new AnnotationConfigReactiveWebApplicationContext();
        annotationConfigReactiveWebApplicationContext
                .addApplicationListener((ApplicationListener<ApplicationEvent>) mock(ApplicationListener.class));
        assertTrue(appConfig.validatorFactoryBean(annotationConfigReactiveWebApplicationContext)
                .getValidationPropertyMap()
                .isEmpty());
    }
}

