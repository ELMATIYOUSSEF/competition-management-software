package com.aftasapi.web.rest;

import com.aftasapi.dto.FishDto;
import com.aftasapi.dto.HuntingDto;
import com.aftasapi.dto.LevelDto;
import com.aftasapi.entity.Fish;
import com.aftasapi.entity.Level;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.handler.response.ResponseMessage;
import com.aftasapi.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/levels")
@RequiredArgsConstructor
public class LevelController {
    private final ModelMapper modelMapper;
    private final LevelService levelService;

    @GetMapping
    public List<?> getAllLevels() {
        return levelService.getAllLevel().stream()
                .map(level -> modelMapper.map(level, LevelDto.class))
                .toList();
    }
    @PostMapping
    public ResponseEntity<?> createLevel( @RequestBody @Validated LevelDto levelDto ) throws Exception {
        Level save = levelService.addLevel(levelDto);
        return ResponseMessage.ok("Level added with successfully",modelMapper.map(save, LevelDto.class));
    }

}
