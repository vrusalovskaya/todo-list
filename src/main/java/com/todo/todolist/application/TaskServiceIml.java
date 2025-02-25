package com.todo.todolist.application;

import com.todo.todolist.persistence.TaskDao;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceIml implements TaskService {
    private final TaskDao taskDao;

    @Value("${task.default}")
    private String defaultTask;

    public TaskServiceIml(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @PostConstruct
    public void loadDefaultTask() {
        add(defaultTask);
    }


    public int add(String content) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("No content provided for task creation");
        }
        return taskDao.add(content);
    }

    public List<Task> getTasks() {
        return taskDao.getTasks().stream().map(TaskMapper.INSTANCE::toModel).toList();
    }

    public boolean updateStatus(int id, boolean completed) {
        return taskDao.updateStatus(id, completed);
    }

}
