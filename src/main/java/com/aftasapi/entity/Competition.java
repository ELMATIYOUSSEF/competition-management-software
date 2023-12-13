package com.aftasapi.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
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
    private Time startTime;
    private Time endTime;
    private Integer numberOfParticipants;
    private String location;
    private Double amount;

    @OneToMany(mappedBy = "competition" ,cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Hunting> hunting;

    @OneToMany(mappedBy = "competition",cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Ranking> ranks;
}
