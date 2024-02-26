package com.aftasapi.factory;

import com.aftasapi.entity.AppRole;
import com.aftasapi.entity.Member;
import com.aftasapi.entity.enums.IdentityDocumentType;
import com.aftasapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class UserFactoty {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public List<Member> createMember(AppRole role) {
        List<Member> memberList = List.of(
                Member.builder().accessionDate(LocalDate.now()).nationality("Franchise")
                        .identityNumber("PA223455").identityDocumentType(IdentityDocumentType.PASSPORT)
                        .name("philipe").familyName("hostnstn").status(true)
                        .roles(List.of(role)).email("mati@gmail.com").password(passwordEncoder.encode("1234")).build()
        );

        for (Member member : memberList) {
            Optional<Member> existingRole = memberRepository.findByEmail(member.getEmail());
            if (existingRole.isEmpty()) {
                try {
                    memberRepository.save(member);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return memberList;
    }
}
