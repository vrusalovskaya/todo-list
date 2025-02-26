package com.todo.todolist.application;

import com.todo.todolist.persistence.TaskDao;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);

    private final TaskDao taskDao;

    @Value("${task.default}")
    private String defaultTask;

    public TaskServiceImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @PostConstruct
    public void loadDefaultTask() {
        add(defaultTask);
    }


    public int add(String content) {
        if (content == null || content.isEmpty()) {
            logger.info("The task was not created due to the absence of the content");
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
