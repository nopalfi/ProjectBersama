package xyz.nopalfi.projectbersama.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.nopalfi.projectbersama.config.AuthenticationRequest;
import xyz.nopalfi.projectbersama.config.AuthenticationResponse;
import xyz.nopalfi.projectbersama.config.RegisterRequest;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.repository.UserRepository;
import xyz.nopalfi.projectbersama.service.AuthenticationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final JwtServiceImpl service;
    private final AuthenticationManager manager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .build();
        repository.save(user);
        String jwtToken = service.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = repository.getByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Username is not found"));
        String jwtToken = service.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
