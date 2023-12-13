package com.aftasapi.service;

import com.aftasapi.dto.FishDto;
import com.aftasapi.entity.Fish;
import com.aftasapi.exception.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FishService  {
    List<Fish> getAllFish();
    Fish getFishByName(String name) throws ResourceNotFoundException;
    Fish addFish(FishDto fishDto) throws ResourceNotFoundException;
    double pointOfFish(String name);

}
