package com.knoldus.radarservice.annotations;

import java.lang.annotation.*;

/** This annotation is used to describe the developer friendly description.
 *
 * @author Romit Saxena
 */
@Repeatable(FieldDescriptorDocs.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface FieldDescriptorDoc {
    String docName() default "";

    String value();

    boolean optional() default false;

    boolean ignored() default false;

    String type() default "";

    Class<?> recursiveType() default String.class;
}

