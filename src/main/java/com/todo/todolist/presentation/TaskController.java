package com.todo.todolist.presentation;

import com.todo.todolist.application.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/tasks")
    public List<TaskDto> getTasks() {
        return taskService.getTasks().stream().map(TaskDtoMapper.INSTANCE::toDto).toList();
    }

    @PostMapping("/task")
    public ResponseEntity<Void> addTask(@RequestBody String content) {
        int id = taskService.add(content);
        URI location = URI.create(String.format("/api/v1/task/%d", id));
        return ResponseEntity.created(location).build();
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateTaskStatus(@PathVariable int id, @RequestBody TaskUpdateRequest request) {
        if (taskService.updateStatus(id, request.isCompleted())) {
            return ResponseEntity.ok("Task status updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task for updating not found");
        }
    }
}
