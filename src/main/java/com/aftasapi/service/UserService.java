package com.aftasapi.service;

import com.aftasapi.entity.Member;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserDetailsService userDetailsService();
    Member getCurrentUser();
    Member findByUsername(String username);
}
