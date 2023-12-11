package com.aftasapi.service;

import com.aftasapi.entity.Member;
import com.aftasapi.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface MemberService {
    Member save(Member member);

    List<Member> findAll(Pageable pageable);

    Member findById(Long memberId) throws Exception;

    Optional<Member> findByIdentityNumber(String identityNumber);

    Member update(Member updatedMember) throws Exception;

    void deleteMember(Long memberId) throws ResourceNotFoundException;
    Page<Member> getMembersByname(String name, Pageable pageable);
}
