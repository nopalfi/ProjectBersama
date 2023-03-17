package xyz.nopalfi.projectbersama.service;

import xyz.nopalfi.projectbersama.config.AuthenticationRequest;
import xyz.nopalfi.projectbersama.config.AuthenticationResponse;
import xyz.nopalfi.projectbersama.config.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
