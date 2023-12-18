package com.aftasapi.dto;

import com.aftasapi.annotations.FutureDays;
import com.aftasapi.entity.Hunting;
import com.aftasapi.entity.Ranking;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompetitionDTO implements Serializable {
    private String code;
    @FutureDays
    private LocalDate date;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
    private Integer numberOfParticipants;
    @NotBlank(message = "Location cannot be blank")
    private String location;
    @Min(value = 0, message = "Amount of fish cannot be negative")
    private int amount;
    private List<Hunting> hunting;
    private List<Ranking> ranks;
}
