package com.aftasapi.web.rest;

import com.aftasapi.dto.HuntingDto;
import com.aftasapi.dto.vm.RequestHuntingVM;
import com.aftasapi.entity.Hunting;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.handler.response.ResponseMessage;
import com.aftasapi.service.HuntingService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hunting")
public class HuntingController {
    private final HuntingService huntingService;
    private final ModelMapper modelMapper;


    @GetMapping
    public List<?> getAllHunting( @ParameterObject Pageable pageable) {
        return huntingService.getAllHuntings(pageable).stream()
                .map(hunting -> modelMapper.map(hunting, HuntingDto.class))
                .toList();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getHuntingById(@PathVariable Long id) throws ResourceNotFoundException {
        Hunting hunting = huntingService.getHuntingById(id);
        return ResponseMessage.ok( "Success", hunting);
    }

    @PostMapping
    public ResponseEntity<?> addHunting(@Validated @RequestBody RequestHuntingVM requestHuntingVM) throws Exception {
        Hunting hunting = huntingService.addHunting(requestHuntingVM);
        return ResponseMessage.created("Competition created successfully",hunting );
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateMember(@RequestBody HuntingDto huntingDto) throws ResourceNotFoundException {
        Hunting competition = huntingService.updateHunting(huntingDto);
        return ResponseMessage.created("Competition updated successfully", competition);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) throws ResourceNotFoundException {
        Hunting hunting = huntingService.deleteHunting(id);
        return ResponseMessage.ok("Hunting deleted successfully" ,hunting);
    }
}
