package com.aftasapi.service;


import com.aftasapi.entity.Ranking;
import com.aftasapi.entity.embedded.RankingId;
import com.aftasapi.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface RankingService {
    void addRanking(Ranking ranking);
    Ranking findRankingByMemberAndCompetition(Long member_id , String codeCompetition) throws ResourceNotFoundException;
    List<Ranking> scoreCompetition(String codeCompetition) throws Exception;
    Optional<Ranking> findByRankingID(RankingId ranking);
}
