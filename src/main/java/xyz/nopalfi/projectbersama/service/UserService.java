package xyz.nopalfi.projectbersama.service;

import xyz.nopalfi.projectbersama.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getUsers();
    User getUserByUsername(String username);
    User getUserByUuid(UUID uuid);
}
