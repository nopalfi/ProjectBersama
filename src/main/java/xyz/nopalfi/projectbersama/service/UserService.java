package xyz.nopalfi.projectbersama.service;

import xyz.nopalfi.projectbersama.entity.User;

import java.util.List;

public interface UserService extends DaoService<User> {
    User getUserByUsername(String username);

}
