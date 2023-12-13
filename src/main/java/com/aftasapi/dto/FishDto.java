package com.aftasapi.dto;

import lombok.Value;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * DTO for {@link com.aftasapi.entity.Fish}
 */
@Value
public class FishDto implements Serializable {
    @NotBlank(message = "name cannot be empty and it doesn't accepted duplication names")
    @Size(min = 3, max = 30)
    private String name;
    @NotBlank(message = "averageWeight cannot be blank")
    @Min(value = 0, message = "averageWeight cannot be negative")
    @Positive
    private double averageWeight;

}