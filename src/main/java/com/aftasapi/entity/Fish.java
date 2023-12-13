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
public class Fish {

    @Id
    private String name;
    private Double averageWeight;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Level level;
}
