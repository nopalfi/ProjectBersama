package xyz.nopalfi.projectbersama.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.service.impl.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class WebController {

    private final UserServiceImpl userService;

    @Autowired
    public WebController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> usersList() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

}
