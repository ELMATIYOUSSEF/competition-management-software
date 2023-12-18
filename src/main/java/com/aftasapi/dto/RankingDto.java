package com.aftasapi.dto;

import com.aftasapi.entity.Member;
import com.aftasapi.entity.embedded.RankingId;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

/**
 * DTO for {@link com.aftasapi.entity.Ranking}
 */
public class RankingDto {
    private RankingId id;

    private Integer ranks;
    private Integer score;
    private Member member;
}