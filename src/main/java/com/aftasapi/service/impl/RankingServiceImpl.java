package com.aftasapi.service.impl;

import com.aftasapi.entity.Competition;
import com.aftasapi.entity.Hunting;
import com.aftasapi.entity.Member;
import com.aftasapi.entity.Ranking;
import com.aftasapi.entity.embedded.RankingId;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.repository.CompetitionRepository;
import com.aftasapi.repository.RankingRepository;
import com.aftasapi.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RankingServiceImpl  implements RankingService {
    private final RankingRepository rankingRepository ;
    private final CompetitionRepository competitionRepository;
    private final HuntingService huntingService ;
    private final FishService fishService;
    private final MemberService memberService;
    @Override
    public void addRanking(Ranking ranking) {
        log.info("Saved with successfully Ranking {} ", ranking);
        rankingRepository.save(ranking);
    }

    @Override
    public List<Ranking> findRankingByMemberAndCompetition(String codeCompetition) throws ResourceNotFoundException {
        List<Ranking> rankings = rankingRepository.findRankingsByCompetition(codeCompetition);
        if (rankings.isEmpty()) {
            throw new ResourceNotFoundException("No Ranking found");
        }
        return rankings;
    }


    public void calculateScoreMember(Competition competition , Member member) throws Exception {
       // Member member = memberService.findById(member_id);
        List<Hunting> allHunting = huntingService.getAllHuntingByCompetitionCode(competition.getCode());
        List<Hunting> listHunting = allHunting.stream().filter(hunting -> hunting.getMember().equals(member)).toList();
        int totalFish =0;
        for (Hunting hunting:listHunting) {
            double pointOfFish = fishService.pointOfFish(hunting.getFish().getName());
            totalFish += (int) (pointOfFish* hunting.getNumberOfFish());
        }
        RankingId rankingId =   RankingId.builder()
                .competitionCode(competition.getCode())
                .memberId(member.getId())
                .build();

        Optional<Ranking> optional = rankingRepository.findById(rankingId);
        if (optional.isEmpty()) throw  new Exception(" Ranking not Found !!");
        optional.get().setScore(totalFish);
        rankingRepository.save(optional.get());
    }
    public List<Ranking> scoreCompetition(String codeCompetition) throws Exception {
        Competition competition  = competitionRepository.findByCode(codeCompetition).
                orElseThrow(() -> new ResourceNotFoundException( "not found any competition whit this code  :"+ codeCompetition));
        List<Member> members = memberService.getMembersByCompetition(codeCompetition);
        if (members.isEmpty()) throw  new Exception(" this competition has no member");
        for (Member member : members) {
            calculateScoreMember(competition, member);
        }
       return updateRankInRanking(competition);
    }

    public List<Ranking> updateRankInRanking(Competition competition){
        List<Ranking> rankings = rankingRepository.getRankingsByCompetitionOrderByScoreDesc(competition);
        List<Ranking> rankingList = new ArrayList<>();
        rankings.forEach(ranking -> {
            ranking.setRanks(rankings.indexOf(ranking) + 1);
           Ranking ranking1 = rankingRepository.save(ranking);
           rankingList.add(ranking1);
        });
        return rankingList ;
    }
    public Optional<Ranking> findByRankingID(RankingId ranking){
        return rankingRepository.findById(ranking);
    }

}
