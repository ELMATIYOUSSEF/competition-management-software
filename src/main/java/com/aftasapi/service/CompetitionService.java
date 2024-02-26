package com.aftasapi.service;

import com.aftasapi.dto.CompetitionDTO;
import com.aftasapi.entity.Competition;
import com.aftasapi.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public interface CompetitionService {
    List<Competition> findAll();

    Competition save(CompetitionDTO competition) throws IllegalArgumentException, ResourceNotFoundException;

   Competition findByDate(LocalDate date);

   Competition findByCode(String code) throws ResourceNotFoundException;
    Competition updateCompetition(CompetitionDTO competition) throws ResourceNotFoundException;
    Competition deleteCompetition(String id) throws Exception;
    Page<Competition> getCompetitionsByName(String name, Pageable pageable);
    Competition registerMemberInCompetition(Long memberId, String competitionCode) throws Exception ;
}
