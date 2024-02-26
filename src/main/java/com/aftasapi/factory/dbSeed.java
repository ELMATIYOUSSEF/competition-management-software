package com.aftasapi.factory;
import com.aftasapi.entity.AppRole;
import com.aftasapi.entity.Member;
import com.aftasapi.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class dbSeed {
    private  final RoleFactory roleFactory ;
    private  final UserFactoty userFactoty ;
    @Bean
    CommandLineRunner start(){
        return args -> {
                List<AppRole> appRoleList = roleFactory.createRoles();
            List<AppRole> collect = appRoleList.stream().filter(appRole -> appRole.getName().equals(Role.ROLE_ADMIN.name())).toList();
            if (!collect.isEmpty()){
                List<Member> members = userFactoty.createMember(collect.get(0));
                System.out.println(members);
            }
        };
    }
}
