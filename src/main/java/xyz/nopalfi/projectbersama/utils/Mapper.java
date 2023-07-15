package xyz.nopalfi.projectbersama.utils;

import org.springframework.stereotype.Component;
import xyz.nopalfi.projectbersama.dto.ProjectDTO;
import xyz.nopalfi.projectbersama.dto.TaskDTO;
import xyz.nopalfi.projectbersama.dto.TeamDTO;
import xyz.nopalfi.projectbersama.dto.UserDTO;
import xyz.nopalfi.projectbersama.entity.Project;
import xyz.nopalfi.projectbersama.entity.Task;
import xyz.nopalfi.projectbersama.entity.Team;
import xyz.nopalfi.projectbersama.entity.User;

import java.util.stream.Collectors;

@Component
public class Mapper {
    public ProjectDTO toProjectDto(Project project) {
        return new ProjectDTO(project.getUuid(), project.getTasks().stream().map(this::toTaskDto).collect(Collectors.toList()), project.getTitle(), project.getDescription(), project.getProjectCreated(), project.getProjectModified(), project.getDeadline(), project.getIsDone(), toUserDto(project.getOwner()), project.getTeams().stream().map(this::toTeamDto).collect(Collectors.toSet()));
    }

    public TaskDTO toTaskDto(Task task) {
        return new TaskDTO(task.getUuid(), task.getTask(), task.getIsDone(), task.getTaskCreated(), task.getTaskModified(), task.getDeadline(), toUserDto(task.getOwner()));
    }

    public UserDTO toUserDto(User user) {
        return new UserDTO(user.getUuid(), user.getUsername());
    }

    public TeamDTO toTeamDto(Team team) {
        return new TeamDTO(team.getUuid(), team.getUsers().stream().map(this::toUserDto).collect(Collectors.toList()), toUserDto(team.getTeamOwner()));
    }
}
