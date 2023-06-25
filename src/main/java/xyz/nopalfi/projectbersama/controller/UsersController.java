package xyz.nopalfi.projectbersama.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.service.impl.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/")
public class UsersController {

    private final UserServiceImpl userService;

    @Autowired
    public UsersController (UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<User>> usersList() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> getUserByUUid(@PathVariable String uuid) {
        return new ResponseEntity<>(userService.getUserByUuid(uuid), HttpStatus.OK);
    }

    @GetMapping("/user/username/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }
}
