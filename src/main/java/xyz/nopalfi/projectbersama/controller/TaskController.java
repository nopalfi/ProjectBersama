package xyz.nopalfi.projectbersama.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.nopalfi.projectbersama.entity.Task;
import xyz.nopalfi.projectbersama.service.impl.TaskServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/tasks/")
public class TaskController {

    private final TaskServiceImpl taskService;

    @Autowired
    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTasks() {
        return new ResponseEntity<>(taskService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/task/uuid/{uuid}")
    public ResponseEntity<Task> getTaskByUUid(@PathVariable String uuid) {
        return new ResponseEntity<>(taskService.getTaskByUuid(UUID.fromString(uuid)), HttpStatus.OK);
    }

}
