package xyz.nopalfi.projectbersama.service;

import xyz.nopalfi.projectbersama.entity.Task;
import xyz.nopalfi.projectbersama.entity.User;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface TaskService {

    List<Task> getAll();
    Task newTask(Task task);
    Task updateTaskByUuid(UUID uuid, Task task);
    void deleteTaskByUuid(UUID uuid);
    List<Task> getTasksByProjectUuid(UUID uuid);
    List<Task> getTasksByOwner(User user);
    Task getTaskByUuid(UUID uuid);
    Boolean getIsTaskDoneByUuid(UUID uuid);
    Instant getCreatedAtInstantByUuid(UUID uuid);
    Instant getModifiedInstantByUuid(UUID uuid);
    Instant getDeadlineByUuid(UUID uuid);

}
