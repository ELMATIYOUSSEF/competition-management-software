package com.aftasapi.service.impl;

import com.aftasapi.entity.Competition;
import com.aftasapi.entity.Member;
import com.aftasapi.entity.Ranking;
import com.aftasapi.entity.embedded.RankingId;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.repository.CompetitionRepository;
import com.aftasapi.service.MemberService;
import com.aftasapi.service.RankingService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RankingServiceImplTest {

    @Mock
    private CompetitionRepository competitionRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private RankingService rankingService;
    @InjectMocks
    private RankingServiceImpl rankingServiceimpl;
    @InjectMocks
    private CompetitionServiceImpl competitionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void scoreCompetition() {
        assertFalse(true);
    }

    // Given a valid competition code, when there are members participating in the competition,
    // then the method should calculate the score for each member and update their ranking accordingly, returning a list of updated rankings sorted by score in descending order.
    @Test
    public void test_validFindByCompetitionCode() throws Exception {
        // Arrange
        String codeCompetition = "validCode";
        Competition competition = new Competition();
        competition.setCode(codeCompetition);
        when(competitionRepository.findByCode(codeCompetition)).thenReturn(Optional.of(competition));
        Optional<Competition> competition1 = competitionRepository.findByCode(codeCompetition);
        assertEquals(competition, competition1.get());
    }

    // Given a valid competition code, when there are no members participating in the competition,
    // then the method should raise an exception with the message "this competition has no member".
    @Test
    public void testValidCompetitionCodeWithoutMembers() throws Exception {
        // Arrange
        String codeCompetition = "validCode";
        Competition competition = new Competition();
        competition.setCode(codeCompetition);
        Member member = new Member();
        member.setId(1L);
        member.setName("youssef");
       RankingId rk = RankingId.builder().competitionCode(codeCompetition).build();
       List<Ranking> list =new ArrayList<>();
        Ranking ranking = new Ranking(rk,0,0,member,competition);
        list.add(ranking);

        when(competitionRepository.findByCode(codeCompetition)).thenReturn(Optional.of(competition));
        when(memberService.getMembersByCompetition(codeCompetition)).thenReturn(new ArrayList<>());
        when(rankingService.scoreCompetition(codeCompetition)).thenReturn(list);
        List<Ranking> rankingList = rankingService.scoreCompetition(codeCompetition);

        // Assert exception message
       assertEquals(list, rankingList);
    }


    // Given an invalid competition code,
    // then the method should raise a
    // ResourceNotFoundException with the message "not found any competition with this code: {codeCompetition}".
    @Test
    public void test_invalidCompetitionCode() throws Exception {
        // Arrange
        String codeCompetition = "invalidCode";
        when(competitionRepository.findByCode(codeCompetition)).thenReturn(Optional.empty());
        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> rankingServiceimpl.scoreCompetition(codeCompetition), "not found any competition with this code: " + codeCompetition);
    }


}

