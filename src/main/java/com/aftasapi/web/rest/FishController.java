package com.aftasapi.web.rest;

import com.aftasapi.dto.CompetitionDTO;
import com.aftasapi.dto.FishDto;
import com.aftasapi.entity.Competition;
import com.aftasapi.entity.Fish;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.handler.response.ResponseMessage;
import com.aftasapi.service.FishService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fishes")
@RequiredArgsConstructor
public class FishController {
    private final ModelMapper modelMapper;
    private final FishService fishService;

    @GetMapping
    public List<FishDto> getAllFish() {
   return fishService.getAllFish().stream()
                .map(fish -> modelMapper.map(fish, FishDto.class))
                .toList();
    }
    @PostMapping
    public ResponseEntity<?> createFish( @RequestBody @Validated FishDto fishDto ) throws ResourceNotFoundException {
        Fish save = fishService.addFish(fishDto);
        return ResponseMessage.ok("Fish added with successfully",modelMapper.map(save, FishDto.class));
    }

}
