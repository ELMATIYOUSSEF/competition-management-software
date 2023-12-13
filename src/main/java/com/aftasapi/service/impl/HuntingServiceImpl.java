package com.aftasapi.service.impl;

import com.aftasapi.dto.HuntingDto;
import com.aftasapi.dto.vm.RequestHuntingVM;
import com.aftasapi.entity.*;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.repository.CompetitionRepository;
import com.aftasapi.repository.HuntingRepository;
import com.aftasapi.repository.MemberRepository;
import com.aftasapi.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HuntingServiceImpl implements HuntingService {
    private final HuntingRepository huntingRepository;
    private final ModelMapper modelMapper;
    private final MemberService memberService;
    private final CompetitionRepository competitionRepository ;
    private final FishService fishService ;
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
    public Hunting addHunting(RequestHuntingVM huntingVM) throws Exception {

        Member member = memberService.findById(huntingVM.getId_User());
        Fish fish = fishService.getFishByName(huntingVM.getName_Fish());
        Competition competition = competitionRepository.findByCode(huntingVM.getCode_Competition()).orElseThrow(()-> new ResourceNotFoundException("not found this competition"));

        if(huntingVM.getPoids_Fish() < fish.getAverageWeight()) throw new Exception( " this Fish is less than Average Weight !!");
        Hunting hunting = Hunting.builder()
                .numberOfFish(huntingVM.getNumberOfFish())
                .competition(competition)
                .fish(fish)
                .member(member)
                .build();

        Optional<Hunting> existingHunting = huntingRepository.findById(hunting.getId());
        if(existingHunting.isPresent()){
            existingHunting.get().setNumberOfFish(huntingVM.getNumberOfFish()+1);
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
    public List<Hunting> getAllHuntingByCompetitionCode(String competitionCode) {
        return huntingRepository.findHuntingByCompetitionCode(competitionCode);
    }

}
