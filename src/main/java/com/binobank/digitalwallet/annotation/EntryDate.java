package com.binobank.digitalwallet.annotation;

import com.binobank.digitalwallet.validator.EntryDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = EntryDateValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EntryDate {

    public String message() default "invalid date";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
