package com.aftasapi.dto;

import com.aftasapi.annotations.FutureDays;
import com.aftasapi.annotations.TimeRange;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TimeRange(startTime = "startTime", endTime = "endTime", message = "End time must be greater than start time")
public class CompetitionDTO implements Serializable {
    private String code;
    @NotNull
    @FutureDays
    private LocalDate date;
    @NotNull
    private Time startTime;

    @NotNull
    private Time endTime;

    @Min(value = 2, message = "Number of participants cannot be negative and well be 2 as min ")
    private Integer numberOfParticipant;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    @Min(value = 0, message = "Amount of fish cannot be negative")
    private int amount;
}
