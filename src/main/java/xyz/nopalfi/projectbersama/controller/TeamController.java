package xyz.nopalfi.projectbersama.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.nopalfi.projectbersama.dto.TeamDTO;
import xyz.nopalfi.projectbersama.dto.UserDTO;
import xyz.nopalfi.projectbersama.entity.CustomResponseBody;
import xyz.nopalfi.projectbersama.entity.Team;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.service.impl.TeamServiceImpl;
import xyz.nopalfi.projectbersama.utils.Mapper;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/teams")
public class TeamController {

    @Value("${api.version}")
    private String apiVersion;
    private final Mapper mapper;

    private final TeamServiceImpl teamService;

    public TeamController(Mapper mapper, TeamServiceImpl teamService) {
        this.mapper = mapper;
        this.teamService = teamService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<List<TeamDTO>>> getAllTeams(HttpServletRequest request) {
        CustomResponseBody<List<TeamDTO>> response = new CustomResponseBody<>();
        response.setMessage("Get All Teams");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        response.setData(teamService.getAll().stream().map(mapper::toTeamDto).collect(Collectors.toList()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<TeamDTO>> newTeam(@RequestBody Team team, HttpServletRequest request) {
        CustomResponseBody<TeamDTO> response = new CustomResponseBody<>();
        response.setMessage("Created A New Team");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        response.setData(mapper.toTeamDto(teamService.save(team)));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/team/users/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<List<UserDTO>>> getAllUsersInTeamByUuid(@PathVariable String uuid, HttpServletRequest request) {
        CustomResponseBody<List<UserDTO>> response = new CustomResponseBody<>();
        response.setMessage("Get All Users In A Team");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        response.setData(teamService.getAllUsersInTeamUuid(uuid).stream().map(mapper::toUserDto).collect(Collectors.toList()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/team/owner/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<UserDTO>> getUserTeamOwnerUuid(@PathVariable String uuid, HttpServletRequest request) {
        CustomResponseBody<UserDTO> response = new CustomResponseBody<>();
        response.setMessage("Get A User Team Owner");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        response.setData(mapper.toUserDto(teamService.getUserTeamOwnerUuid(uuid)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/team/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<String>> deleteTeamByUuid(@PathVariable String uuid, HttpServletRequest request) {
        CustomResponseBody<String> response = new CustomResponseBody<>();
        teamService.delete(uuid);
        response.setMessage("Deleted A Team");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(null);
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
