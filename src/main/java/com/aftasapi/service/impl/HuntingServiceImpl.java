package com.aftasapi.service.impl;

import com.aftasapi.dto.HuntingDto;
import com.aftasapi.entity.Competition;
import com.aftasapi.entity.Hunting;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.repository.HuntingRepository;
import com.aftasapi.service.HuntingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HuntingServiceImpl implements HuntingService {
    private final HuntingRepository huntingRepository;
    private final ModelMapper modelMapper;
    @Override
    public Page<Hunting> getHuntingsByname(String name, Pageable pageable) {
        return null;
    }

    @Override
    public List<Hunting> getAllHuntings(Pageable pageable) {
        log.info("Fetching By name Hunting for page {} of size {}", pageable.getPageSize(), pageable.getPageSize());
        return huntingRepository.findAll(pageable).stream().toList();
    }

    @Override
    public Hunting getHuntingById(Long id) throws ResourceNotFoundException {
        return huntingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hunting not found with id" + id));
    }

    @Override
    public Hunting addHunting(HuntingDto huntingDto) {
        Hunting hunting = modelMapper.map(huntingDto,Hunting.class);
        Optional<Hunting> existingHunting = huntingRepository.findById(hunting.getId());
        if(existingHunting.isPresent()){
            existingHunting.get().setNumberOfFish(huntingDto.getNumberOfFish());
            return huntingRepository.save(existingHunting.get());
        }
        log.info("Saved with successfully Hunting {} ",hunting);
        return huntingRepository.save(hunting);

    }

    @Override
    public Hunting updateHunting(HuntingDto huntingDto) throws ResourceNotFoundException {
        Hunting hunting = modelMapper.map(huntingDto, Hunting.class);
        Hunting existingHunting  = getHuntingById(hunting.getId());
        existingHunting.setNumberOfFish(huntingDto.getNumberOfFish());
        log.info("Update with successfully Hunting {} ",existingHunting);
        return huntingRepository.save(existingHunting);
    }

    @Override
    public Hunting deleteHunting(Long id) throws ResourceNotFoundException {
        log.info("Deleted hunting {} ",id);
        Hunting hunting = getHuntingById(id);
        if(hunting.getId() != null) {
            huntingRepository.deleteById(id);
            return hunting;
        }
        throw new ResourceNotFoundException("Error try Deleting again !! ");
    }
}
