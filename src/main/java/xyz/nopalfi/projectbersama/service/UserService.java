package xyz.nopalfi.projectbersama.service;

import xyz.nopalfi.projectbersama.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getUsers();
    String getUsernameByUuid(UUID uuid);
    UUID getUuidByUsername(String username);
    String getEmailByUsername(String username);
    String getUsernameByEmail(String email);
}
