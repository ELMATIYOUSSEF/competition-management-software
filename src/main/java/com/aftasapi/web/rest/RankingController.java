package com.aftasapi.web.rest;

import com.aftasapi.dto.CompetitionDTO;
import com.aftasapi.dto.RankingDto;
import com.aftasapi.entity.Ranking;
import com.aftasapi.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ranking")
public class RankingController {
    private final RankingService rankingService ;
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<RankingDto>>  CalculateRanking(@RequestParam String code) throws Exception {
        List<RankingDto> rankingList = rankingService.scoreCompetition(code).stream().map(ranking -> modelMapper.map(ranking, RankingDto.class))
                .toList();
        return ResponseEntity.ok(rankingList);
    }

}
