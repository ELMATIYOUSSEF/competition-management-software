package com.aftasapi.dto.vm;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Value
@Getter
@Setter
@Builder
public class RequestHuntingVM {
    Long id;
    @NotNull
    @Positive
    @Min(value = 0, message = "number of fish cannot be negative")
    Integer numberOfFish;
    @NotNull(message = "Code Competition is mandatory")
    String code_Competition;
    @NotNull(message = "Member is mandatory")
    Long id_User;
    @NotBlank(message = "name cannot be empty and it doesn't accepted duplication names")
    @Size(min = 3, max = 30)
    String name_Fish;
    @NotNull
    Double poids_Fish;
}
