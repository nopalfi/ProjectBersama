package xyz.nopalfi.projectbersama.service;

import xyz.nopalfi.projectbersama.entity.Task;

import java.util.List;

public interface TaskService extends DaoService<Task> {

    List<Task> getTasksByProjectUuid(String uuid);
    List<Task> getTasksByOwnerUuid(String uuid);

}
