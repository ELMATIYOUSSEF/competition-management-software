package com.aftasapi.service.impl;

import com.aftasapi.entity.Member;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.repository.MemberRepository;
import com.aftasapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public Member save(Member member) {
        canMemberBeSaved(member);
        log.info("Saved with successfully Member {} ",member);
        return memberRepository.save(member);
    }

    private void canMemberBeSaved(Member member) {
        if(member.getId() != null) {
            throw new IllegalArgumentException("Member already exists");
        }
        if (findByIdentityNumber(member.getIdentityNumber()).isPresent()) {
            throw new IllegalArgumentException("Member with identity number " + member.getIdentityNumber() + " already exists");
        }
    }

    @Override
    public List<Member> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable).stream().toList();
    }

    @Override
    public Member findById(Long memberId) throws Exception {
        return memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("Member not found with id" + memberId));
    }

    @Override
    public Optional<Member> findByIdentityNumber(String identityNumber) {
        return memberRepository.findByIdentityNumber(identityNumber);
    }

    @Override
    public Member update(Member updatedMember) throws Exception {
        Member member = findById(updatedMember.getId());
        if(! member.getIdentityNumber().equals(updatedMember.getIdentityNumber())) {
            throw new IllegalArgumentException("Identity number cannot be changed");
        }

        return memberRepository.save(updatedMember);
    }

    @Override
    public void deleteMember(Long memberId) throws ResourceNotFoundException {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id " + memberId));
        memberRepository.deleteById(memberId);
    }
    @Override
    public Page<Member> getMembersByname(String name, Pageable pageable) {
        log.info("Fetching By name Member for page {} of size {}", pageable.getPageNumber(), pageable.getPageSize());
        return memberRepository.findByNameContaining(name, pageable);
    }

    public List<Member> getMembersByCompetition(String competitionCode) {
        return memberRepository.findMembersByCompetition(competitionCode);
    }
}
