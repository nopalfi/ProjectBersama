package xyz.nopalfi.projectbersama.service;

import xyz.nopalfi.projectbersama.entity.Project;
import xyz.nopalfi.projectbersama.entity.Task;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ProjectService {
    Project newProject(Project project);
    Project updateProjectByUuid(UUID uuid, Project project);
    Project addNewTaskByUuid(UUID uuid, Task task);
    Project deleteATaskfromProjectByUuid(UUID uuid, Task task);
    void deleteProjectByUuid(UUID uuid);
    List<Project> getAllProject();
    Project getProjectByUuid(UUID uuid);
    String getProjectTitleByUuid(UUID uuid);
    String getProjectDescriptionByUuid(UUID uuid);
    Boolean getProjectIsDoneByUuid(UUID uuid);
    Instant getProjectCreatedInstantByUuid(UUID uuid);
    Instant getProjectModifiedInstantByUuid(UUID uuid);
    Instant getProjectDeadlineInstantByUuid(UUID uuid);

}
