package com.aftasapi.service.impl;

import com.aftasapi.dto.FishDto;
import com.aftasapi.entity.Competition;
import com.aftasapi.entity.Fish;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.repository.FishRepository;
import com.aftasapi.service.FishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FishServiceImpl implements FishService {
    private final FishRepository fishRepository ;
    private final ModelMapper modelMapper;
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
}
