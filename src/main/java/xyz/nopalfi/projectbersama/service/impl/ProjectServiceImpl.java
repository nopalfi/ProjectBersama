package xyz.nopalfi.projectbersama.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xyz.nopalfi.projectbersama.entity.Project;
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
        return repository.save(project);
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
