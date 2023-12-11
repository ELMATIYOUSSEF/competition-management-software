package com.aftasapi.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * DTO for {@link com.aftasapi.entity.Level}
 */
@Value
public class LevelDto implements Serializable {
    @NotNull
    @Positive
    Long code;
    @NotBlank
    String description;
    @NotNull
    @Positive
    Integer point;
}