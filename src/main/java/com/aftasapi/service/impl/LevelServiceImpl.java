package com.aftasapi.service.impl;

import com.aftasapi.dto.LevelDto;
import com.aftasapi.entity.Level;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.repository.LevelRepository;
import com.aftasapi.service.LevelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {
    private final LevelRepository levelRepository ;
    private final ModelMapper modelMapper;
    @Override
    public List<Level> getAllLevel() {
        log.info("Level All Level's ");
        return levelRepository.findAll();
    }

    @Override
    public Level getLevelByName(Long code) throws ResourceNotFoundException {
        log.info("Fetching By Level Code");
        return levelRepository.findByCode(code).orElseThrow(()-> new ResourceNotFoundException("No Level with this Code"));
    }

    @Override
    public Level addLevel(LevelDto LevelDto) throws Exception {
        Level levelMapped = modelMapper.map(LevelDto, Level.class);
        Optional<Level> optionalLevel = levelRepository.findByCode(levelMapped.getCode());
        if(optionalLevel.isPresent()) throw new Exception("this level is already Exist ");
        Integer maxPoint = levelRepository.findMaxPoint();
        if(maxPoint>levelMapped.getPoint()) throw new Exception("Error you can not add Level less than " + maxPoint);
        log.info("Saved with successfully Level {} ",levelMapped);
        return levelRepository.save(levelMapped);
    }
}
