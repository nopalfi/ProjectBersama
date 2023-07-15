package xyz.nopalfi.projectbersama.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xyz.nopalfi.projectbersama.entity.Team;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.errorhandler.UuidNotFoundException;
import xyz.nopalfi.projectbersama.repository.TeamRepository;
import xyz.nopalfi.projectbersama.service.TeamService;
import xyz.nopalfi.projectbersama.utils.UUIDConverter;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository repository;

    @Autowired
    public TeamServiceImpl(TeamRepository repository) {
        this.repository = repository;
    }



    @Override
    public List<User> getAllUsersInTeamUuid(String uuid) {
        Team team = repository.findByUuid(UUIDConverter.convert(uuid)).orElseThrow(() -> new UuidNotFoundException("Team with UUID: "+uuid+" not found", HttpStatus.NOT_FOUND));
        return team.getUsers();
    }

    @Override
    public User getUserTeamOwnerUuid(String uuid) {
        Team team = repository.findByUuid(UUIDConverter.convert(uuid)).orElseThrow(() -> new UuidNotFoundException("Team with UUID: "+uuid+" not found", HttpStatus.NOT_FOUND));
        return team.getTeamOwner();
    }

    @Override
    public List<Team> getAll() {
        return repository.findAll();
    }

    @Override
    public Team save(Team team) {
        return repository.save(team);
    }

    @Override
    public Team get(String uuid) {
        return repository.findByUuid(UUIDConverter.convert(uuid)).orElseThrow(() -> new UuidNotFoundException("Team with UUID: "+uuid+" not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public void update(String uuid, Team team) {
        Team oldTeam = repository.findByUuid(UUIDConverter.convert(uuid)).orElseThrow(() -> new UuidNotFoundException("Team with UUID: "+uuid+" not found", HttpStatus.NOT_FOUND));
        oldTeam.setTeamOwner(team.getTeamOwner());
        oldTeam.setUsers(team.getUsers());
        repository.save(oldTeam);
    }

    @Override
    public void delete(String uuid) {
        Team team = repository.findByUuid(UUIDConverter.convert(uuid)).orElseThrow(() -> new UuidNotFoundException("Team with UUID: "+uuid+" not found", HttpStatus.NOT_FOUND));
        repository.delete(team);
    }
}
