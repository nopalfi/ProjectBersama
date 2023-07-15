package xyz.nopalfi.projectbersama.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.nopalfi.projectbersama.dto.ProjectDTO;
import xyz.nopalfi.projectbersama.entity.CustomResponseBody;
import xyz.nopalfi.projectbersama.entity.Project;
import xyz.nopalfi.projectbersama.entity.Task;
import xyz.nopalfi.projectbersama.service.impl.ProjectServiceImpl;
import xyz.nopalfi.projectbersama.service.impl.TaskServiceImpl;
import xyz.nopalfi.projectbersama.utils.Mapper;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectController {

    @Value("${api.version}")
    private String apiVersion;
    private final ProjectServiceImpl projectService;
    private final TaskServiceImpl taskService;
    private final Mapper mapper;

    public ProjectController(ProjectServiceImpl projectService, TaskServiceImpl taskService, Mapper mapper) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.mapper = mapper;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<List<ProjectDTO>>> getAllProjects(HttpServletRequest request) {
        List<Project> projectList = projectService.getAll();
        CustomResponseBody<List<ProjectDTO>> response = new CustomResponseBody<>();
        response.setMessage("Get All Project");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(projectList.stream().map(mapper::toProjectDto).collect(Collectors.toList()));
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<ProjectDTO>> newProject(@RequestBody Project project, HttpServletRequest request) {
        CustomResponseBody<ProjectDTO> response = new CustomResponseBody<>();
        response.setMessage("Created New Project");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(mapper.toProjectDto(project));
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        projectService.save(project);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/project/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<ProjectDTO>> updateProjectByUuid(@PathVariable String uuid, @RequestBody Project project, HttpServletRequest request) {
        projectService.update(uuid, project);
        CustomResponseBody<ProjectDTO> response = new CustomResponseBody<>();
        response.setMessage("Project Updated");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(mapper.toProjectDto(project));
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/project/{uuid}/isDone/{isDone}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<Project>> updateIsDoneProjectByUuid(@PathVariable String uuid, @PathVariable Boolean isDone, HttpServletRequest request) {
        Project project = projectService.updateIsDoneProjectByUuid(uuid, isDone);
        CustomResponseBody<Project> response = new CustomResponseBody<>();
        response.setMessage("Project Updated");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(project);
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/project/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<Project>> getProjectByUuid(@PathVariable String uuid, HttpServletRequest request) {
        Project project = projectService.get(uuid);
        CustomResponseBody<Project> response = new CustomResponseBody<>();
        response.setMessage("Project Found");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(project);
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/project/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<String>> deleteProjectByUuid(@PathVariable String uuid, HttpServletRequest request) {
        projectService.delete(uuid);
        CustomResponseBody<String> response = new CustomResponseBody<>();
        response.setMessage("Project Deleted");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(null);
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/project/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<Project>> addNewTaskByUuid(@PathVariable String uuid, @RequestBody Task task, HttpServletRequest request) {
        Task newTask = taskService.save(task);
        Project project = projectService.addNewTaskByUuid(uuid, newTask);
        CustomResponseBody<Project> response = new CustomResponseBody<>();
        response.setMessage("Added New Task to a Project");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(project);
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("project/{projectUuid}/task/{taskUuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<Project>> deleteATaskfromProjectByUuid(@PathVariable String projectUuid, @PathVariable String taskUuid, HttpServletRequest request) {
        Project project = projectService.deleteATaskfromProjectByUuid(projectUuid, taskUuid);
        CustomResponseBody<Project> response = new CustomResponseBody<>();
        response.setMessage("A Task deleted from a Project");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(project);
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
