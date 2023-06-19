package xyz.nopalfi.projectbersama.service;

import xyz.nopalfi.projectbersama.entity.Team;
import xyz.nopalfi.projectbersama.entity.User;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TeamService {

    List<Team> getAllTeams();
    Team newTeam(Team team);
    Set<User> getAllUsersInTeamUuid(UUID uuid);
    User getUserTeamOwnerUuid(UUID uuid);

}
