package com.aftasapi.repository;

import com.aftasapi.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByIdentityNumber(String identityNumber);
    Page<Member> findByNameContaining(String name, Pageable pageable);

    @Query("SELECT m FROM Member m " +
            "WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(m.familyName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(m.identityNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Member> searchMembers(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query("SELECT m FROM Member m JOIN m.rankings r WHERE r.competition.code = :competitionCode")
    List<Member> findMembersByCompetition(@Param("competitionCode") String competitionCode);
}
