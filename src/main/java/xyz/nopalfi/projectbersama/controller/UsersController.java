package xyz.nopalfi.projectbersama.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.nopalfi.projectbersama.dto.TeamDTO;
import xyz.nopalfi.projectbersama.dto.UserDTO;
import xyz.nopalfi.projectbersama.entity.CustomResponseBody;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.service.impl.UserServiceImpl;
import xyz.nopalfi.projectbersama.utils.Mapper;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users/")
public class UsersController {

    @Value("${api.version}")
    private String apiVersion;
    private final Mapper mapper;
    private final UserServiceImpl userService;

    @Autowired
    public UsersController (Mapper mapper, UserServiceImpl userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<List<UserDTO>>> usersList(HttpServletRequest request) {
        CustomResponseBody<List<UserDTO>> response = new CustomResponseBody<>();
        response.setMessage("Get All Users");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        response.setData(userService.getAll().stream().map(mapper::toUserDto).collect(Collectors.toList()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<UserDTO>> getUserByUuid(@PathVariable String uuid, HttpServletRequest request) {
        CustomResponseBody<UserDTO> response = new CustomResponseBody<>();
        response.setMessage("Get A User");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        response.setData(mapper.toUserDto(userService.get(uuid)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/username/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<UserDTO>> getUserByUsername(@PathVariable String username, HttpServletRequest request) {
        CustomResponseBody<UserDTO> response = new CustomResponseBody<>();
        response.setMessage("Get A User");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        response.setData(mapper.toUserDto(userService.getUserByUsername(username)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
