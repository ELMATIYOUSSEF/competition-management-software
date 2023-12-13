package com.aftasapi.service;

import com.aftasapi.dto.HuntingDto;
import com.aftasapi.dto.vm.RequestHuntingVM;
import com.aftasapi.entity.Hunting;
import com.aftasapi.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface HuntingService {
    Page<Hunting> getHuntingsByname(String name, Pageable pageable);
    List<Hunting> getAllHuntings(Pageable pageable);
    Hunting getHuntingById(Long id) throws ResourceNotFoundException;
    Hunting addHunting(RequestHuntingVM huntingVM) throws Exception;
    Hunting updateHunting(HuntingDto huntingDto) throws ResourceNotFoundException;
    Hunting deleteHunting(Long id) throws ResourceNotFoundException;
    List<Hunting> getAllHuntingByCompetitionCode(String competitionCode);
}
