package xyz.nopalfi.projectbersama.service;


public interface JwtService {

    String extractUsername(String jwtToken);
}
