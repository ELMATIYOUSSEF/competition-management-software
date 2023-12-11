package com.aftasapi.service.impl;

import com.aftasapi.dto.CompetitionDTO;
import com.aftasapi.entity.Competition;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.PageRequest.of;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompetitionServiceImpl implements com.aftasapi.service.CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Competition> findAll(Pageable pageable) {
        log.info("Fetching By name Competition for page {} of size {}", pageable.getPageSize(), pageable.getPageSize());
        return competitionRepository.findAll(pageable).stream().toList();
    }
    @Override
    public Page<Competition> getCompetitionsByName(String name, Pageable pageable) {
        log.info("Fetching By name Competition for page {} of size {}", pageable.getPageNumber(),pageable.getPageSize());
        return competitionRepository.findByCodeContaining(name, pageable);
    }

    @Override
    public Competition save(CompetitionDTO competitionDTO) throws IllegalArgumentException, ResourceNotFoundException {
        competitionDTO.setCode(generateCompetitionCode(competitionDTO.getLocation()));
        Competition competitionMapped = modelMapper.map(competitionDTO, Competition.class);
        canCompetitionBeSaved(competitionMapped);
        log.info("Saved with successfully Competition {} ",competitionMapped);
        return competitionRepository.save(competitionMapped);
    }


    @Override
    public Competition findByDate(Date date) {
        log.info("Find by date Competition {} ",date);
        return competitionRepository.findByDate(date).orElseThrow();
    }

    @Override
    public Competition findByCode(String code) throws ResourceNotFoundException {
        log.info("Find by code Competition {} ",code);
        return competitionRepository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException("Competition not found with code " + code));
    }
    @Override
    public Competition updateCompetition(CompetitionDTO competitionDTO) throws ResourceNotFoundException {
        Competition competition = modelMapper.map(competitionDTO, Competition.class);
        Competition existingCompetition = findByCode(competition.getCode());
        existingCompetition.setDate(competition.getDate());
        existingCompetition.setLocation(competition.getLocation());
        existingCompetition.setEndTime(competition.getEndTime());
        existingCompetition.setStartTime(competition.getStartTime());
        existingCompetition.setNumberOfParticipant(competition.getNumberOfParticipant());
        existingCompetition.setAmount(competition.getAmount());
        log.info("Update with successfully Competition {} ",competition);
        return competitionRepository.save(existingCompetition);
    }

    @Override
    public Competition deleteCompetition(String code) throws Exception {
        log.info("Deleted Competition {} ",code);
       return competitionRepository.deleteByCode(code).orElseThrow( () -> new Exception("Error try to deleted again !! ") );
    }
    private void canCompetitionBeSaved(Competition competition) throws IllegalArgumentException, ResourceNotFoundException {
        if (!findByCode(competition.getCode()).getCode().isEmpty()){
            log.warn("Competition already exists with same code: {} " , competition.getCode());
            throw new IllegalArgumentException("Competition already exists with same code: " + competition.getCode());
        }
    }
    public static String generateCompetitionCode(String location) {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
        String formattedDate = dateFormat.format(currentDate);
        location = location.toLowerCase();
        char[] charArray = location.toCharArray();
        StringBuilder res = new StringBuilder();
        for( int i =0 ; i < 3 ; i++){
            res.append(charArray[i]);
        }
        return res + "-" + formattedDate.replace("-", "");
    }
}
