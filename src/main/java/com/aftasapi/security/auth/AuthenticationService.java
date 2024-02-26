package com.aftasapi.security.auth;

import com.aftasapi.dto.vm.LoginRequest;
import com.aftasapi.dto.vm.RegisterRequest;
import com.aftasapi.entity.AppRole;
import com.aftasapi.entity.Member;
import com.aftasapi.entity.enums.Role;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.exception.UnauthorizedException;
import com.aftasapi.repository.MemberRepository;
import com.aftasapi.repository.RoleRepository;
import com.aftasapi.security.jwt.JwtService;
import com.aftasapi.security.jwt.TokenType;
import com.aftasapi.service.UserService;
import com.aftasapi.utils.CustomError;
import com.aftasapi.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final MemberRepository userRepository;
    private final UserService userService ;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder ;
    private final JwtService jwtService ;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse login(LoginRequest login) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword())
        );
        var user = userRepository.findByEmail(login.getEmail()).orElseThrow(()-> new UsernameNotFoundException("No User with this Email !!"));
        String accessToken = jwtService.generateToken(user, TokenType.ACCESS_TOKEN);
        String refreshToken = jwtService.generateToken(user, TokenType.REFRESH_TOKEN);
        return JwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtAuthenticationResponse register(RegisterRequest registerRequest) throws Exception {
        Optional<Member> appUser = userRepository.findByEmail(registerRequest.getEmail());
        if(appUser.isPresent()) throw new Exception("this Email is Already Exist ") ;
        AppRole name = roleRepository.findByName(Role.ROLE_CLIENT.name()).orElseThrow(()-> new UsernameNotFoundException("No Role with this name"));

        Member user = Member.builder()
                .familyName(registerRequest.getFamilyName())
                .name(registerRequest.getName())
                .identityDocumentType(registerRequest.getIdentityDocumentType())
                .identityNumber(registerRequest.getIdentityNumber())
                .nationality(registerRequest.getNationality())
                .accessionDate(registerRequest.getAccessionDate())
                .status(false)
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(List.of(name))
                .build();
        userRepository.save(user);
        String accessToken = jwtService.generateToken(user, TokenType.ACCESS_TOKEN);
        String refreshToken = jwtService.generateToken(user, TokenType.REFRESH_TOKEN);
        return JwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    public JwtAuthenticationResponse refreshToken(String  refreshToken) throws ValidationException {
        if(jwtService.isTokenValid(refreshToken, TokenType.REFRESH_TOKEN)) {
            String username = jwtService.extractUserName(refreshToken);
            var user = userRepository.findByEmail(username).orElseThrow(() -> new ValidationException(new CustomError("email", "User not found")));
            var accessToken = jwtService.generateToken(user, TokenType.ACCESS_TOKEN);
            return JwtAuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        throw new UnauthorizedException("Refresh token is invalid");
    }

    public Member memberAuthenticate() {
        return userService.getCurrentUser();
    }
    public Member activeAccount(Long id) throws ResourceNotFoundException {
        Optional<Member> member = userRepository.findById(id);
        if (member.isEmpty()) throw new ResourceNotFoundException("this Member not Found");
        member.get().setStatus(true);
        userRepository.save(member.get());
        return member.get();
    }
}
