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
    public List<Task> getAll() {
        return repository.findAll();
    }

    @Override
    public Task newTask(Task task) {
        Instant now = Instant.now();
        task.setTaskCreated(now);
        task.setTaskModified(now);
        return repository.save(task);
    }

    @Override
    public Task updateTaskByUuid(UUID uuid, Task task) {
        if (repository.findByUuid(uuid).isPresent()) {
            Task oldTask = repository.findByUuid(uuid).get();
            oldTask.setTask(task.getTask());
            oldTask.setTaskModified(Instant.now());
            oldTask.setOwner(task.getOwner());
            oldTask.setDeadline(task.getDeadline());
            oldTask.setIsDone(task.getIsDone());
            return repository.save(oldTask);
        } else {
            throw new UuidNotFoundException("Task with UUID: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteTaskByUuid(UUID uuid) {
        if (repository.findByUuid(uuid).isPresent()) {
            Task task = repository.findByUuid(uuid).get();
            repository.delete(task);
        } else {
            throw new UuidNotFoundException("Task with UUID: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Task> getTasksByProjectUuid(UUID uuid) {
        Project project = projectService.getProjectByUuid(uuid);
        return project.getTasks();
    }

    @Override
    public List<Task> getTasksByOwner(User user) {
        return repository.findByOwner(user);
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

    @Override
    public Instant getDeadlineByUuid(UUID uuid) {
        if (repository.findByUuid(uuid).isPresent()) {
            return repository.findByUuid(uuid).get().getDeadline();
        } else {
            throw new UuidNotFoundException("Task with UUID: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND);
        }
    }


}
