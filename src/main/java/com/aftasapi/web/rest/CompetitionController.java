package com.aftasapi.web.rest;

import com.aftasapi.dto.CompetitionDTO;
import com.aftasapi.entity.Competition;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.handler.response.ResponseMessage;
import com.aftasapi.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/competitions")
@RequiredArgsConstructor
public class CompetitionController {

    private final CompetitionService competitionService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> getAllCompetition(
            @ParameterObject Pageable pageable
            ) {
        return ResponseEntity.ok(competitionService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> createCompetition( @RequestBody @Validated CompetitionDTO competitionDTO)
            throws ResourceNotFoundException {
        Competition save = competitionService.save(competitionDTO);
        return ResponseMessage.ok("Competition deleted with successfully",modelMapper.map(save, CompetitionDTO.class));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompetition(@RequestBody @Validated CompetitionDTO competitionDTO) throws ResourceNotFoundException {
        Competition competition = competitionService.updateCompetition(competitionDTO);
        return ResponseMessage.created("Competition updated successfully",modelMapper.map(competition, CompetitionDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompetition(@PathVariable String id) throws Exception {
        Competition competition = competitionService.deleteCompetition(id);
        return ResponseMessage.ok("Competition deleted with successfully", modelMapper.map(competition, CompetitionDTO.class));
    }

    @GetMapping("search")
    public ResponseEntity<?> searchCompetition(@RequestParam String name,
                                          @ParameterObject Pageable pageable) {
        Page<Competition> competitions = competitionService.getCompetitionsByName(name,pageable);
        return ResponseMessage.ok("Success", competitions);
    }
}
