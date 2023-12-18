package com.aftasapi.entity;

import com.aftasapi.entity.embedded.RankingId;
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
public class Ranking {

    @EmbeddedId
    private RankingId id;

    private Integer ranks;
    private Integer score;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    @MapsId("memberId")
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "competition_id")
    @MapsId("competitionCode")
    @JsonIgnore
    private Competition competition;
}
