package com.aftasapi.security.auth;

import com.aftasapi.dto.MemberDTO;
import com.aftasapi.dto.vm.LoginRequest;
import com.aftasapi.dto.vm.RegisterRequest;
import com.aftasapi.entity.Member;
import com.aftasapi.entity.enums.Role;
import com.aftasapi.exception.ResourceNotFoundException;
import com.aftasapi.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;


@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ModelMapper modelMapper;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest login) {
        JwtAuthenticationResponse result = authenticationService.login(login);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody @Valid RegisterRequest register) throws ValidationException, Exception {
        JwtAuthenticationResponse result = authenticationService.register(register);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/token/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(HttpServletRequest request) throws ValidationException, com.aftasapi.utils.ValidationException {
        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            throw new UnauthorizedException("Refresh token is missing");
        }
        String token = authorization.substring(7);
        JwtAuthenticationResponse result = authenticationService.refreshToken(token);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/member/authenticate")
    public ResponseEntity<MemberDTO> memberAuthenticate() {
        Member result = authenticationService.memberAuthenticate();
        return ResponseEntity.ok((modelMapper.map(result, MemberDTO.class)));
    }





}
