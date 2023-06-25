package xyz.nopalfi.projectbersama.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import xyz.nopalfi.projectbersama.config.JwtUtils;
import xyz.nopalfi.projectbersama.entity.CustomResponseBody;
import xyz.nopalfi.projectbersama.entity.LoginRequest;
import xyz.nopalfi.projectbersama.service.impl.UserServiceImpl;

import java.time.Instant;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserServiceImpl userService, JwtUtils jwtUtils, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<CustomResponseBody<String>> authenticate(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);
        CustomResponseBody<String> response = new CustomResponseBody<>();
        response.setData(jwt);
        response.setMessage("Authenticated Successfully");
        response.setTimestamp(Instant.now().getEpochSecond());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
