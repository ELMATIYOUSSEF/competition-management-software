package com.aftasapi.annotations;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TimeRangeValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeRange {
    String message() default "End time must be greater than start time";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    String startTime() default "startTime";

    String endTime() default "endTime";
}

