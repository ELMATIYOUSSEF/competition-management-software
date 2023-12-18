package com.aftasapi.service.impl;

import com.aftasapi.dto.FishDto;
import com.aftasapi.entity.Competition;
import com.aftasapi.entity.Fish;
import com.aftasapi.entity.Level;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.repository.FishRepository;
import com.aftasapi.repository.LevelRepository;
import com.aftasapi.service.FishService;
import com.aftasapi.service.LevelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FishServiceImpl implements FishService {
    private final FishRepository fishRepository ;
    private final ModelMapper modelMapper;
    private final LevelRepository levelRepository ;
    @Override
    public List<Fish> getAllFish() {
        log.info("Fetching All Fish's ");
        return fishRepository.findAll();
    }

    @Override
    public Fish getFishByName(String name) throws ResourceNotFoundException {
        log.info("Fetching By name Fish's ");
       return fishRepository.findByName(name).orElseThrow(()-> new ResourceNotFoundException("No Fish with this name"));
    }

    @Override
    public Fish addFish(FishDto fishDto) throws ResourceNotFoundException {

        Fish fishMapped = modelMapper.map(fishDto, Fish.class);
        Optional<Fish> optionalFish = fishRepository.findByName(fishMapped.getName());
        if(optionalFish.isEmpty()){
            log.info("Saved with successfully Fish {} ",fishMapped);
            return fishRepository.save(fishMapped);
        }
        throw new ResourceNotFoundException("this name is already Exist ");
    }
    // get point of this fish
    public double pointOfFish(String name){
       return fishRepository.findPointByName(name);
    }
    public void generateAndSaveFakeData() {
        List<Level> levels = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Level level = Level.builder()
                    .description("Level " + i)
                    .point(i * 10)
                    .build();
            levels.add(level);
        }
        levelRepository.saveAll(levels);

        List<Fish> fishes = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Fish fish = Fish.builder()
                    .name("Fish " + i)
                    .averageWeight(Math.floor(Math.random() *10)*5)
                    .level(levels.get(i % levels.size()))
                    .build();
            fishes.add(fish);
        }
        fishRepository.saveAll(fishes);
    }
}
