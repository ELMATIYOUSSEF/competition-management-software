package com.aftasapi.web.rest;


import com.aftasapi.dto.MemberDTO;
import com.aftasapi.dto.MemberInputDTO;
import com.aftasapi.dto.PaginatedResponseMemberDto;
import com.aftasapi.entity.Competition;
import com.aftasapi.entity.Member;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.handler.response.ResponseMessage;
import com.aftasapi.security.auth.AuthenticationService;
import com.aftasapi.service.MemberService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.PageRequest.of;
@OpenAPIDefinition
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;
    private final AuthenticationService authenticationService ;

    @PostMapping
    @Operation(summary = "Create a new member")
    public MemberDTO createMember( @Valid @RequestBody MemberInputDTO memberInputDTO) {
        Member save = memberService.save(modelMapper.map(memberInputDTO, Member.class));
        return modelMapper.map(save, MemberDTO.class);
    }

    @GetMapping
    @Operation(summary = "Get all members")
    public ResponseEntity<?> getAllMembers(@RequestParam Optional<Integer> page,
                                           @RequestParam Optional<Integer> size) {
     Page<Member> members =   memberService.findAll(page.orElse(0), size.orElse(10));
     PaginatedResponseMemberDto paginatedResponseMemberDto = PaginatedResponseMemberDto.builder()
                .member(members.getContent())
                .totalElements(members.getNumberOfElements())
                .pageNumber(members.getTotalPages())
                .pageSize(members.getSize())
                .totalPages(members.getTotalPages())
                .build();
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .data(paginatedResponseMemberDto)
                        .message("members Retrieved")
                        .status(200)
                        .build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a member by ID")
    public MemberDTO getMemberById(@PathVariable Long id) throws Exception {
        Member member = memberService.findById(id);
        return modelMapper.map(member, MemberDTO.class);
    }

    @PutMapping
    @Operation(summary = "Update a member")
    public MemberDTO updateMember(@RequestBody MemberInputDTO updatedMemberDTO) throws Exception {
        Member update = memberService.update(modelMapper.map(updatedMemberDTO, Member.class));
        return modelMapper.map(update, MemberDTO.class);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a member by ID")
    public void deleteMember(@PathVariable Long id) throws ResourceNotFoundException{
        memberService.deleteMember(id);
    }
    @GetMapping("/search")
    @Operation(summary = "Search members by name")
    public ResponseEntity<?> searchMember(@RequestParam String name, @RequestParam Optional<Integer> page,
                                          @RequestParam Optional<Integer> size) {
        Page<Member> members = memberService.getMembersByname(name,of(page.orElse(0),size.orElse(10)));
        return ResponseMessage.ok("Success",members);
    }
    @PostMapping("/active")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RolesAllowed(value={"ROLE_ADMIN"})
    public ResponseEntity<MemberDTO> ChangeStatus( @RequestParam Long idMember  ) throws ResourceNotFoundException {
        Member result = authenticationService.activeAccount(idMember);
        return ResponseEntity.ok((modelMapper.map(result, MemberDTO.class)));
    }
}
