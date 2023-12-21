package com.aftasapi.service.impl;

import com.aftasapi.dto.HuntingDto;
import com.aftasapi.dto.vm.RequestHuntingVM;
import com.aftasapi.entity.*;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.repository.CompetitionRepository;
import com.aftasapi.repository.HuntingRepository;
import com.aftasapi.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
        Fish fish = fishService.getFishByName(huntingVM.getName_Fish());
        if(huntingVM.getPoids_Fish() < fish.getAverageWeight()) throw new Exception( " this Fish is less than Average Weight !!");
        Member member = memberService.findById(huntingVM.getId_User());
        Competition competition = competitionRepository.findByCode(huntingVM.getCode_Competition()).orElseThrow(()-> new ResourceNotFoundException("not found this competition"));
        checkDateStart(competition);
        List<Hunting> huntingByCompetitionCode = getAllHuntingByCompetitionCode(competition.getCode());
        List<Hunting> hunting1 = huntingByCompetitionCode.stream()
                .filter(hunting -> hunting.getMember().equals(member))
                .toList();
        Hunting hunting = Hunting.builder()
                .competition(competition)
                .fish(fish)
                .member(member)
                .build();

        if(!hunting1.isEmpty()){
            hunting1.get(0).setNumberOfFish(hunting1.get(0).getNumberOfFish()+1);
            return huntingRepository.save(hunting1.get(0));
        }
        hunting.setNumberOfFish(1);
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
    public  void checkDateStart(Competition competition) throws Exception {
        LocalDate currentDate = LocalDate.now();
        LocalTime start = competition.getStartTime();
        LocalDateTime competitionStartDateTime = LocalDateTime.of(currentDate, start);
        if (competitionStartDateTime.isAfter(LocalDateTime.now())) {
            throw new Exception("this competition doesn't Start");
        }
        if(currentDate.isAfter(competition.getDate())) throw new Exception("Time for Save Hunting's is Expired");
    }


}
