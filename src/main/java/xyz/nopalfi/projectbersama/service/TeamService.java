package xyz.nopalfi.projectbersama.service;

import xyz.nopalfi.projectbersama.entity.Team;
import xyz.nopalfi.projectbersama.entity.User;

import java.util.List;

public interface TeamService {

    List<Team> getAllTeams();
    Team newTeam(Team team);
    List<User> getAllUsersInTeamUuid(String uuid);
    User getUserTeamOwnerUuid(String uuid);
    void deleteTeamByUuid(String uuid);

}
