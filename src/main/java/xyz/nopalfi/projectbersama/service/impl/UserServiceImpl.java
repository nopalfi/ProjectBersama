package xyz.nopalfi.projectbersama.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.errorhandler.UuidNotFoundException;
import xyz.nopalfi.projectbersama.repository.UserRepository;
import xyz.nopalfi.projectbersama.service.UserService;
import xyz.nopalfi.projectbersama.utils.UUIDConverter;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public User getUserByUsername(String username) {
        if (repository.getByUsername(username).isPresent()) {
            return repository.getByUsername(username).get();
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User get(String uuid) {
        return repository.getByUuid(UUIDConverter.convert(uuid)).orElseThrow(() -> new UuidNotFoundException("Team with UUID: "+uuid+" not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public void update(String uuid, User user) {
        User oldUser = repository.getByUuid(UUIDConverter.convert(uuid)).orElseThrow(() -> new UuidNotFoundException("Team with UUID: "+uuid+" not found", HttpStatus.NOT_FOUND));
        oldUser.setUsername(user.getUsername());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setSecondName(user.getSecondName());
        oldUser.setEmail(user.getEmail());
        oldUser.setRole(user.getRole());
        oldUser.setTasks(user.getTasks());
        oldUser.setPassword(encoder.encode(user.getPassword()));
        repository.save(oldUser);
    }

    @Override
    public void delete(String uuid) {
        User user = repository.getByUuid(UUIDConverter.convert(uuid)).orElseThrow(() -> new UuidNotFoundException("Team with UUID: "+uuid+" not found", HttpStatus.NOT_FOUND));
        repository.delete(user);
    }
}
