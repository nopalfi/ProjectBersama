package xyz.nopalfi.projectbersama.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.nopalfi.projectbersama.entity.CustomResponseBody;
import xyz.nopalfi.projectbersama.entity.Project;
import xyz.nopalfi.projectbersama.entity.Task;
import xyz.nopalfi.projectbersama.service.impl.ProjectServiceImpl;
import xyz.nopalfi.projectbersama.service.impl.TaskServiceImpl;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectController {

    private final ProjectServiceImpl projectService;
    private final TaskServiceImpl taskService;

    public ProjectController(ProjectServiceImpl projectService, TaskServiceImpl taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProject(), HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<Project>> newProject(@RequestBody Project project) {
        CustomResponseBody<Project> response = new CustomResponseBody<>();
        response.setMessage("Created New Project");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(project);
        projectService.newProject(project);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/project/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<Project>> updateProjectByUuid(@PathVariable String uuid, @RequestBody Project project) {
        projectService.updateProjectByUuid(uuid, project);
        CustomResponseBody<Project> response = new CustomResponseBody<>();
        response.setMessage("Project Updated");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(project);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/project/{uuid}/isDone/{isDone}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<Project>> updateIsDoneProjectByUuid(@PathVariable String uuid, @PathVariable Boolean isDone) {
        Project project = projectService.updateIsDoneProjectByUuid(uuid, isDone);
        CustomResponseBody<Project> response = new CustomResponseBody<>();
        response.setMessage("Project Updated");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(project);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/project/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<Project>> getProjectByUuid(@PathVariable String uuid) {
        Project project = projectService.getProjectByUuid(uuid);
        CustomResponseBody<Project> response = new CustomResponseBody<>();
        response.setMessage("Project Found");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(project);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/project/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<String>> deleteProjectByUuid(@PathVariable String uuid) {
        projectService.deleteProjectByUuid(uuid);
        CustomResponseBody<String> response = new CustomResponseBody<>();
        response.setMessage("Project Deleted");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/project/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<Project>> addNewTaskByUuid(@PathVariable String uuid, @RequestBody Task task) {
        Task newTask = taskService.newTask(task);
        Project project = projectService.addNewTaskByUuid(uuid, newTask);
        CustomResponseBody<Project> response = new CustomResponseBody<>();
        response.setMessage("Added New Task to a Project");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(project);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("project/{projectUuid}/task/{taskUuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<Project>> deleteATaskfromProjectByUuid(@PathVariable String projectUuid, @PathVariable String taskUuid) {
        Project project = projectService.deleteATaskfromProjectByUuid(projectUuid, taskUuid);
        CustomResponseBody<Project> response = new CustomResponseBody<>();
        response.setMessage("A Task deleted from a Project");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(project);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
