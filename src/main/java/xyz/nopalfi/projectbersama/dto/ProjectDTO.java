package xyz.nopalfi.projectbersama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ProjectDTO {
    private UUID uuid;
    private List<TaskDTO> tasks;
    private String title;
    private String description;
    private Instant projectCreated;
    private Instant projectModified;
    private Instant deadline;
    private Boolean isDone;
    private UserDTO user;
    private Set<TeamDTO> teams;
}
