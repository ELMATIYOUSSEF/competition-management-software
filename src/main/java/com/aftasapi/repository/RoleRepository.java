package com.aftasapi.repository;

import com.aftasapi.entity.AppRole;
import com.aftasapi.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<AppRole, Long> {
    @Query("SELECT r FROM AppRole r WHERE r.name = :name")
    Optional<AppRole> findByName(@Param("name") String name);
}
