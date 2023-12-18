package com.aftasapi.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * DTO for {@link com.aftasapi.entity.Hunting}
 */
@Value
@Builder
@Getter
@Setter
public class HuntingDto implements Serializable {
    Long id;
    @NotNull
    @Positive
    @Min(value = 0, message = "number of fish cannot be negative")
    Integer numberOfFish;
}
