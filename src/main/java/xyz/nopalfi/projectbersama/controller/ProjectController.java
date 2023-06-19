package xyz.nopalfi.projectbersama.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.nopalfi.projectbersama.entity.CustomResponseBody;
import xyz.nopalfi.projectbersama.entity.Project;
import xyz.nopalfi.projectbersama.errorhandler.InvalidUUIDException;
import xyz.nopalfi.projectbersama.service.impl.ProjectServiceImpl;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectController {

    private final ProjectServiceImpl projectService;

    public ProjectController(ProjectServiceImpl projectService) {
        this.projectService = projectService;
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
        response.setMessage("Created New Project");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(project);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/project/uuid/{uuid}")
    public ResponseEntity<Project> getProjectByUuid(@PathVariable String uuid) {
        try {
            UUID uuid1 = UUID.fromString(uuid);
            return new ResponseEntity<>(projectService.getProjectByUuid(uuid1), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            throw new InvalidUUIDException("Invalid UUID: "+uuid, HttpStatus.BAD_REQUEST);
        }
    }
}
