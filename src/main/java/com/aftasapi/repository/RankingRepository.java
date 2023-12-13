package com.aftasapi.repository;

import com.aftasapi.entity.Competition;
import com.aftasapi.entity.Ranking;
import com.aftasapi.entity.embedded.RankingId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, RankingId> {
    @Modifying
    @Query("UPDATE Ranking r SET r.score = :totalScore " +
            "WHERE r.member.id = :memberId AND r.competition.code = :competitionCode")
    void updateScore(@Param("totalScore") double totalScore,
                               @Param("memberId") Long memberId,
                               @Param("competitionCode") String competitionCode);


  /*  @Query("SELECT r, RANK() OVER (ORDER BY r.score DESC) FROM Ranking r " +
            "WHERE r.member.id = :memberId AND r.competition.code = :competitionCode")
    Long calculateRank(@Param("memberId") Long memberId,
                                 @Param("competitionCode") String competitionCode); */


 /*   @Modifying
    @Query("UPDATE Ranking r SET r.score = :newScore WHERE r.member.id = :memberId AND r.competition.code = :competitionCode")
    Ranking updateScore(@Param("newScore") double newScore,
                     @Param("memberId") Long memberId,
                     @Param("competitionCode") String competitionCode); */

    @Query("SELECT r FROM Ranking r WHERE r.member.id = :memberId AND r.competition.code = :competitionCode")
    Optional<Ranking> findRankingByMemberAndCompetition(@Param("memberId") Long memberId,
                                               @Param("competitionCode") String competitionCode);

    @Query("SELECT r FROM Ranking r WHERE r.competition = :competition ORDER BY r.score DESC")
    List<Ranking> getRankingsByCompetitionOrderByScoreDesc(@Param("competition") Competition competition);
}
