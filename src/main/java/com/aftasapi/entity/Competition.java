package com.aftasapi.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "competition",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"date", "location"})
        }
)
public class Competition {

    @Id
    private String code;
    private Date date;
    private Time startTime;
    private Time endTime;
    private Integer numberOfParticipant;
    private String location;
    private Double amount;

    @OneToMany(mappedBy = "competition")
    @ToString.Exclude
    private List<Hunting> hunting;

    @OneToMany(mappedBy = "competition")
    @ToString.Exclude
    private List<Ranking> ranks;
}
