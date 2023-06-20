package xyz.nopalfi.projectbersama.service;

import xyz.nopalfi.projectbersama.entity.Project;
import xyz.nopalfi.projectbersama.entity.Task;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ProjectService {
    void newProject(Project project);
    void updateProjectByUuid(UUID uuid, Project project);
    void updateIsDoneProjectByUuid(UUID uuid, Project project, Boolean isDone);
    Project addNewTaskByUuid(UUID uuid, Task task);
    Project deleteATaskfromProjectByUuid(UUID projectUuid, UUID taskUuid);
    void deleteProjectByUuid(UUID uuid);
    List<Project> getAllProject();
    Project getProjectByUuid(UUID uuid);

}
