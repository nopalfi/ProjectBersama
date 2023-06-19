package xyz.nopalfi.projectbersama.service;

import xyz.nopalfi.projectbersama.entity.Task;
import xyz.nopalfi.projectbersama.entity.User;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface TaskService {

    Task newTask(Task task);
    List<Task> getTasksByProjectUuid(UUID uuid);
    List<Task> getTasksByUser(User user);
    Task getTaskByUuid(UUID uuid);
    Boolean getIsTaskDoneByUuid(UUID uuid);
    Instant getCreatedAtInstantByUuid(UUID uuid);
    Instant getModifiedInstantByUuid(UUID uuid);

}
