package com.aftasapi.factory;

import com.aftasapi.entity.AppRole;
import com.aftasapi.entity.enums.Role;
import com.aftasapi.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleFactory {
    private final RoleRepository roleRepository;

    @Transactional
    public List<AppRole> createRoles() {
        List<AppRole> appRoleList = List.of(
                AppRole.builder().name(Role.ROLE_CLIENT.name()).build(),
                AppRole.builder().name(Role.ROLE_ADMIN.name()).build(),
                AppRole.builder().name(Role.ROLE_JURY.name()).build()
        );

        for (AppRole role : appRoleList) {
            Optional<AppRole> existingRole = roleRepository.findByName(role.getName());
            if (existingRole.isEmpty()) {
                try {
                    roleRepository.save(role);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return appRoleList;
    }
}
