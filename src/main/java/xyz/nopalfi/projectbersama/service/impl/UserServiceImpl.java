package xyz.nopalfi.projectbersama.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.errorhandler.UuidNotFoundException;
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
    public User getUserByUuid(UUID uuid) {
        return repository.getByUuid(uuid).orElseThrow(() -> new UuidNotFoundException("UUID "+uuid.toString()+" not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public User getUserByUsername(String username) {
        if (repository.getByUsername(username).isPresent()) {
            return repository.getByUsername(username).get();
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
