package xyz.nopalfi.projectbersama.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xyz.nopalfi.projectbersama.entity.Project;
import xyz.nopalfi.projectbersama.entity.Task;
import xyz.nopalfi.projectbersama.entity.User;
import xyz.nopalfi.projectbersama.errorhandler.UuidNotFoundException;
import xyz.nopalfi.projectbersama.repository.TaskRepository;
import xyz.nopalfi.projectbersama.repository.UserRepository;
import xyz.nopalfi.projectbersama.service.TaskService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;
    private final ProjectServiceImpl projectService;
    private final UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository repository, ProjectServiceImpl projectService, UserRepository userRepository) {
        this.repository = repository;
        this.projectService = projectService;
        this.userRepository = userRepository;
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
    public List<Task> getTasksByOwnerUuid(UUID uuid) {
        if (userRepository.getByUuid(uuid).isPresent()) {
            User user = userRepository.getByUuid(uuid).get();
            return user.getTasks();
        } else {
            throw new UuidNotFoundException("Task with UUID: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Task getTaskByUuid(UUID uuid) {
        if (repository.findByUuid(uuid).isPresent()) {
            return repository.findByUuid(uuid).get();
        } else {
            throw new UuidNotFoundException("Task with UUID: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND);
        }
    }

}
