package com.aftasapi.repository;

import com.aftasapi.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByIdentityNumber(String identityNumber);
    Page<Member> findByNameContaining(String name, Pageable pageable);
}
