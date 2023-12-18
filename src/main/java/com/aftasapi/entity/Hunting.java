package com.aftasapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hunting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numberOfFish;

    @ManyToOne
    private Fish fish;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Competition competition;


}
