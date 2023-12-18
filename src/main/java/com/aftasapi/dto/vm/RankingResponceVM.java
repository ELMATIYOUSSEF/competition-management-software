package com.aftasapi.dto.vm;

import com.aftasapi.dto.RankingDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.List;

@Value
@Getter
@Setter
@Builder
public class RankingResponceVM {
    List<RankingDto> rankingDto ;
}
