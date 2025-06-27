package com.cozyhaven.service;

import com.cozyhaven.dto.*;
import com.cozyhaven.entity.Role;
import com.cozyhaven.entity.User;
import com.cozyhaven.repository.UserRepository;
import com.cozyhaven.config.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder encoder;

    public void register(SignupRequest signUpDto) {
        if (userRepo.existsByUsername(signUpDto.username()))
            throw new RuntimeException("Username is already taken");
        if (userRepo.existsByEmail(signUpDto.email()))
            throw new RuntimeException("Email already registered");

        Set<Role> roles = new HashSet<>();
        if (signUpDto.roles() == null || signUpDto.roles().isEmpty()) {
            roles.add(Role.ROLE_USER);
        } else {
            roles.addAll(signUpDto.roles().stream()
                    .map(r -> switch(r) {
                        case "OWNER" -> Role.ROLE_OWNER;
                        case "ADMIN" -> Role.ROLE_ADMIN;
                        default -> Role.ROLE_USER;
                    }).collect(Collectors.toSet()));
        }

        User user = User.builder()
                .username(signUpDto.username())
                .email(signUpDto.email())
                .password(encoder.encode(signUpDto.password()))
                .roles(roles)
                .build();
        userRepo.save(user);
    }

    public JwtResponse login(LoginRequest loginDto) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));
        User user = (User) auth.getPrincipal();
        String token = jwtUtils.generateJwtToken(user.getUsername());
        return new JwtResponse(token, "Bearer", user.getId(), user.getUsername(), user.getEmail(),
                user.getRoles().stream().map(Role::name).collect(Collectors.toSet()));
    }
}
