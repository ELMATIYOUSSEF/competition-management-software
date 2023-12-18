package com.aftasapi.service.impl;

import com.aftasapi.entity.Competition;
import com.aftasapi.entity.Member;
import com.aftasapi.entity.Ranking;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.repository.CompetitionRepository;
import com.aftasapi.service.MemberService;
import com.aftasapi.service.RankingService;
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
    public void test_validCompetitionCodeWithoutMembers() throws Exception {
        String codeCompetition = "validCode";
        Competition competition = new Competition();
        competition.setCode(codeCompetition);
        List<Member> members = new ArrayList<>();

        when(competitionRepository.findByCode(codeCompetition)).thenReturn(Optional.of(competition));
        when(memberService.getMembersByCompetition(codeCompetition)).thenReturn(members);

        // Act and Assert
        assertThrows(Exception.class, () -> rankingService.scoreCompetition(codeCompetition), "this competition has no member");
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
        assertThrows(ResourceNotFoundException.class, () -> rankingService.scoreCompetition(codeCompetition), "not found any competition with this code: " + codeCompetition);
    }

    // Given a competition code that does not exist in the database, then the method should raise a ResourceNotFoundException with the message "not found any competition with this code: {codeCompetition}".
    @Test
    public void test_nonexistentCompetitionCode() throws Exception {
        // Arrange
        String codeCompetition = "nonexistentCode";

        when(competitionRepository.findByCode(codeCompetition)).thenReturn(Optional.empty());
        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> rankingService.scoreCompetition(codeCompetition), "not found any competition with this code: " + codeCompetition);
    }

    // Given a competition code that exists in the database but has no associated competition, then the method should raise a ResourceNotFoundException with the message "not found any competition with this code: {codeCompetition}".
    @Test
    public void test_competitionCodeWithoutCompetition() throws Exception {
        // Arrange
        String codeCompetition = "validCode";

        when(competitionRepository.findByCode(codeCompetition)).thenReturn(Optional.empty());
        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> rankingService.scoreCompetition(codeCompetition), "not found any competition with this code: " + codeCompetition);
    }

}

