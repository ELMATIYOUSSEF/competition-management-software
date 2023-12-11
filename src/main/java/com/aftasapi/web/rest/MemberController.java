package com.aftasapi.web.rest;


import com.aftasapi.dto.MemberDTO;
import com.aftasapi.dto.MemberInputDTO;
import com.aftasapi.entity.Competition;
import com.aftasapi.entity.Member;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.handler.response.ResponseMessage;
import com.aftasapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;

    @PostMapping
    public MemberDTO createMember(@RequestBody MemberInputDTO memberInputDTO) {
        Member save = memberService.save(modelMapper.map(memberInputDTO, Member.class));
        return modelMapper.map(save, MemberDTO.class);
    }

    @GetMapping
    public List<MemberDTO> getAllMembers(@ParameterObject Pageable pageable) {
        return memberService.findAll(pageable).
                stream()
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .toList();
    }

    @GetMapping("/{id}")
    public MemberDTO getMemberById(@PathVariable Long id) throws Exception {
        Member member = memberService.findById(id);
        return modelMapper.map(member, MemberDTO.class);
    }

    @PutMapping
    public MemberDTO updateMember(@RequestBody MemberInputDTO updatedMemberDTO) throws Exception {
        Member update = memberService.update(modelMapper.map(updatedMemberDTO, Member.class));
        return modelMapper.map(update, MemberDTO.class);
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) throws ResourceNotFoundException{
        memberService.deleteMember(id);
    }
    @GetMapping("search")
    public ResponseEntity<?> searchMember(@RequestParam String name, @ParameterObject Pageable pageable) {
        Page<Member> members = memberService.getMembersByname(name,pageable);
        return ResponseMessage.ok("Success", modelMapper.map(members, MemberDTO.class));
    }
}