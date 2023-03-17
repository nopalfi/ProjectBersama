package xyz.nopalfi.projectbersama.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.repository.UserRepository;
import xyz.nopalfi.projectbersama.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getUsers() {
        return repository.findAll();
    }

    @Override
    public String getUsernameByUuid(UUID uuid) {
        return repository.getReferenceById(uuid).getUsername();
    }

    @Override
    public UUID getUuidByUsername(String username) {
        User user = repository.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return user.getUuid();
    }

    @Override
    public String getEmailByUsername(String username) {
        return repository.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found")).getEmail();
    }

    @Override
    public String getUsernameByEmail(String email) {
        return repository.getByEmail(email).orElseThrow(() -> new RuntimeException("Email not found")).getUsername();
    }
}
