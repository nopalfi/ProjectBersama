package xyz.nopalfi.projectbersama.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.nopalfi.projectbersama.dto.TaskDTO;
import xyz.nopalfi.projectbersama.entity.CustomResponseBody;
import xyz.nopalfi.projectbersama.entity.Task;
import xyz.nopalfi.projectbersama.errorhandler.InvalidUUIDException;
import xyz.nopalfi.projectbersama.service.impl.TaskServiceImpl;
import xyz.nopalfi.projectbersama.utils.Mapper;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/tasks/")
public class TaskController {

    @Value("${api.version}")
    private String apiVersion;
    private final TaskServiceImpl taskService;
    private final Mapper mapper;

    @Autowired
    public TaskController(TaskServiceImpl taskService, Mapper mapper) {
        this.taskService = taskService;
        this.mapper = mapper;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<List<TaskDTO>>> getAllTasks(HttpServletRequest request) {
        CustomResponseBody<List<TaskDTO>> response = new CustomResponseBody<>();
        response.setMessage("Get All Tasks");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(taskService.getAll().stream().map(mapper::toTaskDto).collect(Collectors.toList()));
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/task/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<TaskDTO>> getTaskByUUid(@PathVariable String uuid, HttpServletRequest request) {
        CustomResponseBody<TaskDTO> response = new CustomResponseBody<>();
        response.setMessage("Get A Task");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(mapper.toTaskDto(taskService.get(uuid)));
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/task/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<TaskDTO>> updateTaskByUuid(@PathVariable String uuid, @RequestBody Task task, HttpServletRequest request) {
        taskService.update(uuid, task);
        CustomResponseBody<TaskDTO> response = new CustomResponseBody<>();
        response.setMessage("Updated A Task");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(mapper.toTaskDto(task));
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/task/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<String>> deleteTaskByUuid(@PathVariable String uuid, HttpServletRequest request) {
        taskService.delete(uuid);
        CustomResponseBody<String> response = new CustomResponseBody<>();
        response.setMessage("Deleted A Task");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(null);
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/task/project/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<List<TaskDTO>>> getTasksByProjectUuid(@PathVariable String uuid, HttpServletRequest request) {
        CustomResponseBody<List<TaskDTO>> response = new CustomResponseBody<>();
        response.setMessage("Deleted A Task");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(taskService.getTasksByProjectUuid(uuid).stream().map(mapper::toTaskDto).collect(Collectors.toList()));
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/task/owner/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponseBody<List<TaskDTO>>> getTasksByOwnerUuid(@PathVariable String uuid, HttpServletRequest request) {
        CustomResponseBody<List<TaskDTO>> response = new CustomResponseBody<>();
        response.setMessage("Get Tasks By an Owner");
        response.setTimestamp(Instant.now().getEpochSecond());
        response.setData(taskService.getTasksByOwnerUuid(uuid).stream().map(mapper::toTaskDto).collect(Collectors.toList()));
        response.setPath(request.getRequestURI());
        response.setApiVersion(apiVersion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
