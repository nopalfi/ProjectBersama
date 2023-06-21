package xyz.nopalfi.projectbersama.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.nopalfi.projectbersama.entity.CustomResponseBody;
import xyz.nopalfi.projectbersama.entity.Task;
import xyz.nopalfi.projectbersama.errorhandler.InvalidUUIDException;
import xyz.nopalfi.projectbersama.service.impl.TaskServiceImpl;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/v1/tasks/")
public class TaskController {

    private final TaskServiceImpl taskService;

    @Autowired
    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponseBody<List<Task>>> getAllTasks() {
        CustomResponseBody<List<Task>> response = new CustomResponseBody<>();
        response.setMessage("Get All Tasks");
        response.setTimestamp(Instant.EPOCH.getEpochSecond());
        response.setData(taskService.getAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/task/{uuid}")
    public ResponseEntity<CustomResponseBody<Task>> getTaskByUUid(@PathVariable String uuid) {
        CustomResponseBody<Task> response = new CustomResponseBody<>();
        response.setMessage("Get A Task");
        response.setTimestamp(Instant.EPOCH.getEpochSecond());
        response.setData(taskService.getTaskByUuid(uuid));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/task/{uuid}")
    public ResponseEntity<CustomResponseBody<Task>> updateTaskByUuid(@PathVariable String uuid, @RequestBody Task task) {
        CustomResponseBody<Task> response = new CustomResponseBody<>();
        response.setMessage("Updated A Task");
        response.setTimestamp(Instant.EPOCH.getEpochSecond());
        response.setData(taskService.updateTaskByUuid(uuid, task));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/task/{uuid}")
    public ResponseEntity<CustomResponseBody<String>> deleteTaskByUuid(@PathVariable String uuid) {
        taskService.deleteTaskByUuid(uuid);
        CustomResponseBody<String> response = new CustomResponseBody<>();
        response.setMessage("Deleted A Task");
        response.setTimestamp(Instant.EPOCH.getEpochSecond());
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/task/project/{uuid}")
    public ResponseEntity<CustomResponseBody<List<Task>>> getTasksByProjectUuid(@PathVariable String uuid) {
        CustomResponseBody<List<Task>> response = new CustomResponseBody<>();
        response.setMessage("Deleted A Task");
        response.setTimestamp(Instant.EPOCH.getEpochSecond());
        response.setData(taskService.getTasksByProjectUuid(uuid));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/task/owner/{uuid}")
    public ResponseEntity<CustomResponseBody<List<Task>>> getTasksByOwnerUuid(@PathVariable String uuid) {
        try {
            CustomResponseBody<List<Task>> response = new CustomResponseBody<>();
            response.setMessage("Get Tasks By an Owner");
            response.setTimestamp(Instant.EPOCH.getEpochSecond());
            response.setData(taskService.getTasksByOwnerUuid(uuid));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            throw new InvalidUUIDException("Invalid UUID: "+uuid, HttpStatus.BAD_REQUEST);
        }
    }
}
