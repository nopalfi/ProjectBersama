package xyz.nopalfi.projectbersama.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xyz.nopalfi.projectbersama.entity.Project;
import xyz.nopalfi.projectbersama.entity.Task;
import xyz.nopalfi.projectbersama.errorhandler.UuidNotFoundException;
import xyz.nopalfi.projectbersama.repository.ProjectRepository;
import xyz.nopalfi.projectbersama.service.ProjectService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public Project newProject(Project project) {
        Instant now = Instant.now();
        project.setProjectCreated(now);
        project.setProjectModified(now);
        return repository.save(project);
    }

    @Override
    public Project updateProjectByUuid(UUID uuid, Project project) {
        if (repository.getByUuid(uuid).isPresent()) {
            Project oldProject = repository.getByUuid(uuid).get();
            oldProject.setProjectModified(Instant.now());
            oldProject.setTitle(project.getTitle());
            oldProject.setDescription(project.getDescription());
            oldProject.setTasks(project.getTasks());
            oldProject.setIsDone(project.getIsDone());
            oldProject.setTeams(project.getTeams());
            return repository.save(oldProject);
        }else {
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
    public Project deleteATaskfromProjectByUuid(UUID uuid, Task task) {
        if (repository.getByUuid(uuid).isPresent()) {
            Project project = repository.getByUuid(uuid).get();
            project.deleteTask(task);
            return repository.save(project);
        }else {
            throw new UuidNotFoundException("Project: "+uuid.toString()+" not found", HttpStatus.NOT_FOUND);
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

    @Override
    public String getProjectTitleByUuid(UUID uuid) {
        Project project = repository.getByUuid(uuid).orElseThrow(() -> new UuidNotFoundException("Project: "+uuid.toString()+" is not found", HttpStatus.NOT_FOUND));
        return project.getTitle();
    }

    @Override
    public String getProjectDescriptionByUuid(UUID uuid) {
        Project project = repository.getByUuid(uuid).orElseThrow(() -> new UuidNotFoundException("Project: "+uuid.toString()+" is not found", HttpStatus.NOT_FOUND));
        return project.getDescription();
    }

    @Override
    public Boolean getProjectIsDoneByUuid(UUID uuid) {
        Project project = repository.getByUuid(uuid).orElseThrow(() -> new UuidNotFoundException("Project: "+uuid.toString()+" is not found", HttpStatus.NOT_FOUND));
        return project.getIsDone();
    }

    @Override
    public Instant getProjectCreatedInstantByUuid(UUID uuid) {
        Project project = repository.getByUuid(uuid).orElseThrow(() -> new UuidNotFoundException("Project: "+uuid.toString()+" is not found", HttpStatus.NOT_FOUND));
        return project.getProjectCreated();
    }

    @Override
    public Instant getProjectModifiedInstantByUuid(UUID uuid) {
        Project project = repository.getByUuid(uuid).orElseThrow(() -> new UuidNotFoundException("Project: "+uuid.toString()+" is not found", HttpStatus.NOT_FOUND));
        return project.getProjectModified();
    }

    @Override
    public Instant getProjectDeadlineInstantByUuid(UUID uuid) {
        Project project = repository.getByUuid(uuid).orElseThrow(() -> new UuidNotFoundException("Project: "+uuid.toString()+" is not found", HttpStatus.NOT_FOUND));
        return project.getDeadline();
    }
}
