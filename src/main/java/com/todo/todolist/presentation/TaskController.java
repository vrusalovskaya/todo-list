package com.todo.todolist.presentation;

import com.todo.todolist.application.TaskService;
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
//@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class TaskController {

    private static final Logger logger = LogManager.getLogger(TaskController.class);

    private final TaskService taskService;

    @GetMapping
    public List<TaskDto> getTasks() {
        return taskService.getTasks().stream().map(TaskDtoMapper.INSTANCE::toDto).toList();
    }

    @PostMapping
    public ResponseEntity<Integer> createTask(@RequestBody TaskCreateRequest createDto) { // dto
        int id = taskService.createTask(createDto.getContent());
        URI location = URI.create(String.format("/api/v1/tasks/%d", id));
        logger.info(String.format( "New task created: " + location));
        return ResponseEntity.created(location).body(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTaskStatus(@PathVariable int id, @RequestBody TaskUpdateRequest updateDto) {
        if (taskService.updateTaskStatus(id, updateDto.isCompleted())) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task for updating not found");
        }
    }
}
