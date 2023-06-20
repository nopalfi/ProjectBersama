package xyz.nopalfi.projectbersama.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.nopalfi.projectbersama.entity.CustomResponseBody;
import xyz.nopalfi.projectbersama.entity.Project;
import xyz.nopalfi.projectbersama.entity.Task;
import xyz.nopalfi.projectbersama.errorhandler.InvalidUUIDException;
import xyz.nopalfi.projectbersama.service.impl.ProjectServiceImpl;
import xyz.nopalfi.projectbersama.service.impl.TaskServiceImpl;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProject(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<CustomResponseBody<Project>> newProject(@RequestBody Project project) {
        CustomResponseBody<Project> response = new CustomResponseBody<>();
        response.setMessage("Created New Project");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(project);
        projectService.newProject(project);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/project/uuid/{uuid}")
    public ResponseEntity<CustomResponseBody<Project>> updateProjectByUuid(@PathVariable String uuid, @RequestBody Project project) {
        projectService.updateProjectByUuid(UUID.fromString(uuid), project);
        CustomResponseBody<Project> response = new CustomResponseBody<>();
        response.setMessage("Project Updated");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(project);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/project/uuid/{uuid}/isDone/{isDone}")
    public ResponseEntity<CustomResponseBody<Project>> updateIsDoneProjectByUuid(@PathVariable String uuid, @RequestBody Project project, @PathVariable Boolean isDone) {
        projectService.updateIsDoneProjectByUuid(UUID.fromString(uuid), project, isDone);
        CustomResponseBody<Project> response = new CustomResponseBody<>();
        response.setMessage("Project Updated");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(project);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/project/uuid/{uuid}")
    public ResponseEntity<CustomResponseBody<Project>> getProjectByUuid(@PathVariable String uuid) {
        try {
            UUID uuid1 = UUID.fromString(uuid);
            Project project = projectService.getProjectByUuid(uuid1);
            CustomResponseBody<Project> response = new CustomResponseBody<>();
            response.setMessage("Project Found");
            response.setTimestamp(Instant.now().getEpochSecond());
            response.setData(project);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            throw new InvalidUUIDException("Invalid UUID: "+uuid, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/project/uuid/{uuid}")
    public ResponseEntity<CustomResponseBody<String>> deleteProjectByUuid(@PathVariable String uuid) {
        try {
            UUID uuid1 = UUID.fromString(uuid);
            projectService.deleteProjectByUuid(uuid1);
            CustomResponseBody<String> response = new CustomResponseBody<>();
            response.setMessage("Project Deleted");
            response.setTimestamp(Instant.now().getEpochSecond());
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            throw new InvalidUUIDException("Invalid UUID: " + uuid, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/project/uuid/{uuid}")
    public ResponseEntity<CustomResponseBody<Project>> addNewTaskByUuid(@PathVariable String uuid, @RequestBody Task task) {
        taskService.newTask(task);
        try {
            UUID uuid1 = UUID.fromString(uuid);
            Project project = projectService.addNewTaskByUuid(uuid1, task);
            CustomResponseBody<Project> response = new CustomResponseBody<>();
            response.setMessage("Added New Task to a Project");
            response.setTimestamp(Instant.now().getEpochSecond());
            response.setData(project);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            throw new InvalidUUIDException("Invalid UUID: "+uuid, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("project/uuid/{projectUuid}/task/{taskUuid}")
    public ResponseEntity<CustomResponseBody<Project>> deleteATaskfromProjectByUuid(@PathVariable String projectUuid, @PathVariable String taskUuid) {
        UUID taskUuid1;
        UUID projectUuid1;
        try {
            taskUuid1 = UUID.fromString(taskUuid);
        } catch (IllegalArgumentException ex) {
            throw new InvalidUUIDException("Invalid UUID: "+taskUuid, HttpStatus.BAD_REQUEST);
        }
        try {
           projectUuid1 = UUID.fromString(projectUuid);
        } catch (IllegalArgumentException ex) {
            throw new InvalidUUIDException("Invalid UUID: "+projectUuid, HttpStatus.BAD_REQUEST);
        }
        Project project = projectService.deleteATaskfromProjectByUuid(projectUuid1, taskUuid1);
        CustomResponseBody<Project> response = new CustomResponseBody<>();
        response.setMessage("A Task deleted from a Project");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(project);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
