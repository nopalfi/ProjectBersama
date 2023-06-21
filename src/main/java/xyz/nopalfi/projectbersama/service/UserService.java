package xyz.nopalfi.projectbersama.service;

import xyz.nopalfi.projectbersama.entity.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUserByUsername(String username);
    User getUserByUuid(String uuid);
}
