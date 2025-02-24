package com.todo.todolist.application;

import com.todo.todolist.persistence.TaskDao;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskDao taskDao;

    @Value("${task.default}")
    private String defaultTask;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @PostConstruct
    public void loadDefaultTask() {
        add(defaultTask);
    }


    public int add(String content) {
        return taskDao.add(content);
    }

    public List<Task> getTasks() {
        return taskDao.getTasks().stream().map(TaskMapper.INSTANCE::toModel).toList();
    }

    public boolean updateStatus (int id, boolean completed){
       return taskDao.updateStatus(id, completed);
    }

}
