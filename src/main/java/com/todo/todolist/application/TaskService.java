package com.todo.todolist.application;

import java.util.List;

public interface TaskService {
    int createTask(String content);

    List<Task> getTasks();

    boolean updateTaskStatus(int id, boolean completed);
}
