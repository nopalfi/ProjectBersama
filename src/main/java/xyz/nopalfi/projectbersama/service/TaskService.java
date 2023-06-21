package xyz.nopalfi.projectbersama.service;

import xyz.nopalfi.projectbersama.entity.Task;

import java.util.List;

public interface TaskService {

    List<Task> getAll();
    Task newTask(Task task);
    Task updateTaskByUuid(String uuid, Task task);
    void deleteTaskByUuid(String uuid);
    List<Task> getTasksByProjectUuid(String uuid);
    List<Task> getTasksByOwnerUuid(String uuid);
    Task getTaskByUuid(String uuid);

}
