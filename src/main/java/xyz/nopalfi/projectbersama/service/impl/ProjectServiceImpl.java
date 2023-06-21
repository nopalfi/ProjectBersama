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
import xyz.nopalfi.projectbersama.utils.UUIDConverter;

import java.time.Instant;
import java.util.List;

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
    public void updateProjectByUuid(String uuid, Project project) {
        if (repository.getByUuid(UUIDConverter.convert(uuid)).isPresent()) {
            Project oldProject = repository.getByUuid(UUIDConverter.convert(uuid)).get();
            oldProject.setProjectModified(Instant.now());
            oldProject.setTitle(project.getTitle());
            oldProject.setDescription(project.getDescription());
            oldProject.setTasks(project.getTasks());
            oldProject.setIsDone(project.getIsDone());
            oldProject.setTeams(project.getTeams());
            repository.save(oldProject);
        } else {
            throw new UuidNotFoundException("Project: " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Project updateIsDoneProjectByUuid(String uuid, Boolean isDone) {
        if (repository.getByUuid(UUIDConverter.convert(uuid)).isPresent()) {
            Project oldProject = repository.getByUuid(UUIDConverter.convert(uuid)).get();
            oldProject.setIsDone(isDone);
            return repository.save(oldProject);
        } else {
            throw new UuidNotFoundException("Project: " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public Project addNewTaskByUuid(String uuid, Task task) {
        if (repository.getByUuid(UUIDConverter.convert(uuid)).isPresent()) {
            Project project = repository.getByUuid(UUIDConverter.convert(uuid)).get();
            project.addTask(task);
            return repository.save(project);
        } else {
            throw new UuidNotFoundException("Project: " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Project deleteATaskfromProjectByUuid(String projectUuid, String taskUuid) {
        Project project = repository.getByUuid(UUIDConverter.convert(projectUuid)).orElseThrow(() -> new UuidNotFoundException("Project: "+projectUuid+" not found.", HttpStatus.NOT_FOUND));
        Task task = taskRepository.findByUuid(UUIDConverter.convert(taskUuid)).orElseThrow(() -> new UuidNotFoundException("Task: "+taskUuid+" not found.", HttpStatus.NOT_FOUND));
        project.deleteTask(task);
        return repository.save(project);
    }

    @Override
    public void deleteProjectByUuid(String uuid) {
        if (repository.getByUuid(UUIDConverter.convert(uuid)).isPresent()) {
            Project project = repository.getByUuid(UUIDConverter.convert(uuid)).get();
            repository.delete(project);
        }else {
            throw new UuidNotFoundException("Project: "+uuid+" not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Project> getAllProject() {
        return repository.findAll();
    }

    @Override
    public Project getProjectByUuid(String uuid) {
        if (repository.getByUuid(UUIDConverter.convert(uuid)).isPresent()) {
            return repository.getByUuid(UUIDConverter.convert(uuid)).get();
        }else {
            throw new UuidNotFoundException("Project: "+uuid+" not found", HttpStatus.NOT_FOUND);
        }
    }
}
