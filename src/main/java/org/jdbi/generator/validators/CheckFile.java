package org.jdbi.generator.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckFileValidator.class)
@Documented
//@Repeatable(CheckFile.List.class)
public @interface CheckFile
{
    String message() default "{validation.constraints.File.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}

