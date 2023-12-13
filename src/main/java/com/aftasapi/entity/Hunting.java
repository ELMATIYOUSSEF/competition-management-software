package com.aftasapi.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
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
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    private Competition competition;


}
