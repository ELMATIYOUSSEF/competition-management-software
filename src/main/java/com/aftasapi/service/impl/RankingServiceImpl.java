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
    public Ranking findRankingByMemberAndCompetition(Long member_id , String codeCompetition) throws ResourceNotFoundException {
        return rankingRepository.findRankingByMemberAndCompetition(member_id, codeCompetition).orElseThrow(()-> new ResourceNotFoundException("Not found this member in this competition"));
    }

    public void calculateScoreMember(Competition competition , Long member_id) throws Exception {
        Member member = memberService.findById(member_id);

        List<Hunting> allHunting = huntingService.getAllHuntingByCompetitionCode(competition.getCode());
        List<Hunting> listHunting = allHunting.stream().filter(hunting -> hunting.getMember().equals(member)).toList();
        double totalFish =0;
        for (Hunting hunting:listHunting) {
            double pointOfFish = fishService.pointOfFish(hunting.getFish().getName());
            totalFish += pointOfFish* hunting.getNumberOfFish();
        }
        rankingRepository.updateScore(totalFish, member_id, competition.getCode());


    }
    public List<Ranking> scoreCompetition(String codeCompetition) throws Exception {
        Competition competition  = competitionRepository.findByCode(codeCompetition).
                orElseThrow(() -> new ResourceNotFoundException( "not found any competition whit this code  :"+ codeCompetition));
        List<Member> members = memberService.getMembersByCompetition(codeCompetition);
        if (members.isEmpty()) throw  new Exception(" this competition has no member");
        for (Member member : members) {
            calculateScoreMember(competition, member.getId());
        }
     return UpdateRankInRanking(competition);
    }

    public List<Ranking> UpdateRankInRanking(Competition competition){
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
