package xyz.nopalfi.projectbersama.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.nopalfi.projectbersama.entity.CustomResponseBody;
import xyz.nopalfi.projectbersama.entity.Team;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.service.impl.TeamServiceImpl;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/v1/teams")
public class TeamController {

    private final TeamServiceImpl teamService;

    public TeamController(TeamServiceImpl teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponseBody<List<Team>>> getAllTeams() {
        CustomResponseBody<List<Team>> response = new CustomResponseBody<>();
        response.setMessage("Get All Teams");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(teamService.getAllTeams());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<CustomResponseBody<Team>> newTeam(@RequestBody Team team) {
        CustomResponseBody<Team> response = new CustomResponseBody<>();
        response.setMessage("Created A New Team");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(teamService.newTeam(team));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/team/users/{uuid}")
    public ResponseEntity<CustomResponseBody<List<User>>> getAllUsersInTeamByUuid(@PathVariable String uuid) {
        CustomResponseBody<List<User>> response = new CustomResponseBody<>();
        response.setMessage("Get All Users In A Team");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(teamService.getAllUsersInTeamUuid(uuid));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/team/owner/{uuid}")
    public ResponseEntity<CustomResponseBody<User>> getUserTeamOwnerUuid(@PathVariable String uuid) {
        CustomResponseBody<User> response = new CustomResponseBody<>();
        response.setMessage("Get A User Team Owner");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(teamService.getUserTeamOwnerUuid(uuid));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/team/{uuid}")
    public ResponseEntity<CustomResponseBody<String>> deleteTeamByUuid(@PathVariable String uuid) {
        CustomResponseBody<String> response = new CustomResponseBody<>();
        teamService.deleteTeamByUuid(uuid);
        response.setMessage("Deleted A Team");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
