package com.aftasapi.service.impl;


import com.aftasapi.entity.Member;
import com.aftasapi.repository.MemberRepository;
import com.aftasapi.security.utils.SecurityUtils;
import com.aftasapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MemberRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
    }

    @Override
    public Member findByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }
    @Override
    public Member getCurrentUser() {
        String currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if(currentUserLogin == null)
            throw new BadCredentialsException("No User Authenticated !!");
        return this.findByUsername(currentUserLogin);
    }
}
