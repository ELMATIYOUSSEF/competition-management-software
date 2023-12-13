package com.aftasapi.service;

import com.aftasapi.dto.LevelDto;
import com.aftasapi.entity.Level;
import com.aftasapi.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface LevelService {
    List<Level> getAllLevel();
    Level getLevelByName(Long code) throws ResourceNotFoundException;
    Level addLevel(LevelDto LevelDto) throws Exception;
}
