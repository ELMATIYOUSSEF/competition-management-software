package com.aftasapi.annotations;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import java.lang.annotation.*;
import java.time.LocalDate;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureDaysValidator.class)
@Documented
public @interface FutureDays {

    int min() default 2;

    String message() default "Date must be at least {min} days in the future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class FutureDaysValidator implements ConstraintValidator<FutureDays, LocalDate> {

    private int min;

    @Override
    public void initialize(FutureDays constraintAnnotation) {
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;
        }
        LocalDate currentDate = LocalDate.now();
        LocalDate minFutureDate = currentDate.plusDays(min);

        return !date.isBefore(minFutureDate);
    }
}
