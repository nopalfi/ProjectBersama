package xyz.nopalfi.projectbersama.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xyz.nopalfi.projectbersama.entity.Project;
import xyz.nopalfi.projectbersama.entity.Task;
import xyz.nopalfi.projectbersama.errorhandler.UuidNotFoundException;
import xyz.nopalfi.projectbersama.repository.ProjectRepository;
import xyz.nopalfi.projectbersama.repository.TaskRepository;
import xyz.nopalfi.projectbersama.service.ProjectService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;
    private final TaskRepository taskRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void newProject(Project project) {
        Instant now = Instant.now();
        project.setProjectCreated(now);
        project.setProjectModified(now);
        repository.save(project);
    }

    @Override
    public void updateProjectByUuid(UUID uuid, Project project) {
        if (repository.getByUuid(uuid).isPresent()) {
            Project oldProject = repository.getByUuid(uuid).get();
            oldProject.setProjectModified(Instant.now());
            oldProject.setTitle(project.getTitle());
            oldProject.setDescription(project.getDescription());
            oldProject.setTasks(project.getTasks());
            oldProject.setIsDone(project.getIsDone());
            oldProject.setTeams(project.getTeams());
            repository.save(oldProject);
        } else {
            throw new UuidNotFoundException("Project: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void updateIsDoneProjectByUuid(UUID uuid, Project project, Boolean isDone) {
        if (repository.getByUuid(uuid).isPresent()) {
            Project oldProject = repository.getByUuid(uuid).get();
            oldProject.setIsDone(isDone);
            repository.save(oldProject);
        } else {
            throw new UuidNotFoundException("Project: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public Project addNewTaskByUuid(UUID uuid, Task task) {
        if (repository.getByUuid(uuid).isPresent()) {
            Project project = repository.getByUuid(uuid).get();
            project.addTask(task);
            return repository.save(project);
        }else {
            throw new UuidNotFoundException("Project: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Project deleteATaskfromProjectByUuid(UUID projectUuid, UUID taskUuid) {
        if (repository.getByUuid(projectUuid).isPresent()) {
            Project project = repository.getByUuid(projectUuid).get();
            Task task = taskRepository.findByUuid(taskUuid).orElseThrow(() -> new UuidNotFoundException("Task: "+taskUuid+" not found.", HttpStatus.NOT_FOUND));
            project.deleteTask(task);
            return repository.save(project);
        }else {
            throw new UuidNotFoundException("Project: "+projectUuid.toString()+" not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteProjectByUuid(UUID uuid) {
        if (repository.getByUuid(uuid).isPresent()) {
            Project project = repository.getByUuid(uuid).get();
            repository.delete(project);
        }else {
            throw new UuidNotFoundException("Project: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Project> getAllProject() {
        return repository.findAll();
    }

    @Override
    public Project getProjectByUuid(UUID uuid) {
        if (repository.getByUuid(uuid).isPresent()) {
            return repository.getByUuid(uuid).get();
        }else {
            throw new UuidNotFoundException("Project: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND);
        }
    }
}
