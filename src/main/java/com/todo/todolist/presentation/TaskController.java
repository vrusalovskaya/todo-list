package com.todo.todolist.presentation;

import com.todo.todolist.application.Task;
import com.todo.todolist.application.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public List<TaskDto> getTasks() {
        return taskService.getTasks().stream().map(TaskMapper.INSTANCE::toDto).toList();
    }

    @PostMapping("task")
    public void addTask(String content) {
        taskService.add(new Task(content));
    }

}
