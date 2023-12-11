package com.aftasapi.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalTime;

public class TimeRangeValidator implements ConstraintValidator<TimeRange, Object> {

    private String startTimeFieldName;
    private String endTimeFieldName;

    @Override
    public void initialize(TimeRange constraintAnnotation) {
        this.startTimeFieldName = constraintAnnotation.startTime();
        this.endTimeFieldName = constraintAnnotation.endTime();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Class<?> clazz = value.getClass();

            Field startTimeField = clazz.getDeclaredField(startTimeFieldName);
            Field endTimeField = clazz.getDeclaredField(endTimeFieldName);

            startTimeField.setAccessible(true);
            endTimeField.setAccessible(true);

            LocalTime startTime = (LocalTime) startTimeField.get(value);
            LocalTime endTime = (LocalTime) endTimeField.get(value);

            return startTime == null || endTime == null || endTime.isAfter(startTime);
        } catch (Exception e) {
            return false;
        }
    }
}
