package com.aftasapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
/*@Table(
        name = "competition",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"date", "location"})
        }
)*/
public class Competition {

    @Id
    private String code;
    @Column(unique = true)
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer numberOfParticipants;
    private String location;
    private Double amount;

    @OneToMany(mappedBy = "competition" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hunting> hunting;

    @OneToMany(mappedBy = "competition",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ranking> ranks;
}
