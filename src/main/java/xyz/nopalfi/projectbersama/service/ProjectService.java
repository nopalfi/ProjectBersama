package xyz.nopalfi.projectbersama.service;

import xyz.nopalfi.projectbersama.entity.Project;
import xyz.nopalfi.projectbersama.entity.Task;

import java.util.List;

public interface ProjectService {
    void newProject(Project project);
    void updateProjectByUuid(String uuid, Project project);
    Project updateIsDoneProjectByUuid(String uuid, Boolean isDone);
    Project addNewTaskByUuid(String uuid, Task task);
    Project deleteATaskfromProjectByUuid(String projectUuid, String taskUuid);
    void deleteProjectByUuid(String uuid);
    List<Project> getAllProject();
    Project getProjectByUuid(String uuid);

}
