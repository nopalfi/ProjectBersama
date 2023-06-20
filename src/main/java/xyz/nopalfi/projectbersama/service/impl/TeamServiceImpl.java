package xyz.nopalfi.projectbersama.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xyz.nopalfi.projectbersama.entity.Team;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.errorhandler.UuidNotFoundException;
import xyz.nopalfi.projectbersama.repository.TeamRepository;
import xyz.nopalfi.projectbersama.service.TeamService;

import java.util.List;
import java.util.UUID;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository repository;

    @Autowired
    public TeamServiceImpl(TeamRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Team> getAllTeams() {
        return repository.findAll();
    }

    @Override
    public Team newTeam(Team team) {
        return repository.save(team);
    }


    @Override
    public List<User> getAllUsersInTeamUuid(UUID uuid) {
        Team team = repository.findByUuid(uuid).orElseThrow(() -> new UuidNotFoundException("Team with UUID: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND));
        return team.getUsers();
    }

    @Override
    public User getUserTeamOwnerUuid(UUID uuid) {
        Team team = repository.findByUuid(uuid).orElseThrow(() -> new UuidNotFoundException("Team with UUID: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND));
        return team.getTeamOwner();
    }

    @Override
    public void deleteTeamByUuid(UUID uuid) {
        Team team = repository.findByUuid(uuid).orElseThrow(() -> new UuidNotFoundException("Team with UUID: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND));
        repository.delete(team);
    }
}
