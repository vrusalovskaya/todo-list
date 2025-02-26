package com.todo.todolist.presentation;

import com.todo.todolist.application.TaskService;
import com.todo.todolist.persistence.TaskDaoImpl;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class TaskController {

    private static final Logger logger = LogManager.getLogger(TaskController.class);

    private final TaskService taskService;

    @GetMapping
    public List<TaskDto> getTasks() {
        return taskService.getTasks().stream().map(TaskDtoMapper.INSTANCE::toDto).toList();
    }

    @PostMapping
    public ResponseEntity<Void> addTask(@RequestBody String content) {
        int id = taskService.add(content);
        URI location = URI.create(String.format("/api/v1/tasks/%d", id));
        logger.info(String.format( "New task created: " + location));
        return ResponseEntity.created(location).build();
    }


    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTaskStatus(@PathVariable int id, @RequestBody TaskUpdateRequest request) {
        if (taskService.updateStatus(id, request.isCompleted())) {
            return ResponseEntity.ok("Task status updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task for updating not found");
        }
    }
}
