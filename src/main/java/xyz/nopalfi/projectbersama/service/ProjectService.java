package xyz.nopalfi.projectbersama.service;

import xyz.nopalfi.projectbersama.entity.Project;
import xyz.nopalfi.projectbersama.entity.Task;

import java.util.List;

public interface ProjectService extends DaoService<Project> {

    Project updateIsDoneProjectByUuid(String uuid, Boolean isDone);
    Project addNewTaskByUuid(String uuid, Task task);
    Project deleteATaskfromProjectByUuid(String projectUuid, String taskUuid);

}
