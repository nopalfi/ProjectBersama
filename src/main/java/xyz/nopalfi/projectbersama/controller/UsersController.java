package xyz.nopalfi.projectbersama.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.service.impl.UserServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/")
public class UsersController {

    private final UserServiceImpl userService;

    @Autowired
    public UsersController (UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> usersList() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/uuid/{uuid}")
    public ResponseEntity<User> getUserByUUid(@PathVariable String uuid) {
        return new ResponseEntity<>(userService.getUserByUuid(UUID.fromString(uuid)), HttpStatus.OK);
    }

    @GetMapping("/user/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }
}
