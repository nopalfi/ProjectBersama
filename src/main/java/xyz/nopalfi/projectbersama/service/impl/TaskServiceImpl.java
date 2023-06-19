package xyz.nopalfi.projectbersama.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xyz.nopalfi.projectbersama.entity.Project;
import xyz.nopalfi.projectbersama.entity.Task;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.errorhandler.UuidNotFoundException;
import xyz.nopalfi.projectbersama.repository.TaskRepository;
import xyz.nopalfi.projectbersama.service.TaskService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;
    private final ProjectServiceImpl projectService;

    @Autowired
    public TaskServiceImpl(TaskRepository repository, ProjectServiceImpl projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }


    @Override
    public Task newTask(Task task) {
        task.setTaskCreated(Instant.now());
        task.setTaskModified(Instant.now());
        return repository.save(task);
    }

    @Override
    public List<Task> getTasksByProjectUuid(UUID uuid) {
        Project project = projectService.getProjectByUuid(uuid);
        return project.getTasks();
    }

    @Override
    public List<Task> getTasksByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public Task getTaskByUuid(UUID uuid) {
        if (repository.findByUuid(uuid).isPresent()) {
            return repository.findByUuid(uuid).get();
        } else {
            throw new UuidNotFoundException("Task with UUID: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Boolean getIsTaskDoneByUuid(UUID uuid) {
        if (repository.findByUuid(uuid).isPresent()) {
            return repository.findByUuid(uuid).get().getIsDone();
        } else {
            throw new UuidNotFoundException("Task with UUID: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Instant getCreatedAtInstantByUuid(UUID uuid) {
        if (repository.findByUuid(uuid).isPresent()) {
            return repository.findByUuid(uuid).get().getTaskCreated();
        } else {
            throw new UuidNotFoundException("Task with UUID: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Instant getModifiedInstantByUuid(UUID uuid) {
        if (repository.findByUuid(uuid).isPresent()) {
            return repository.findByUuid(uuid).get().getTaskModified();
        } else {
            throw new UuidNotFoundException("Task with UUID: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND);
        }
    }
}
