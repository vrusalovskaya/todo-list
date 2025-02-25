package com.todo.todolist.application;

import java.util.List;

public interface TaskService {
    int add(String content);

    List<Task> getTasks();

    boolean updateStatus(int id, boolean completed);
}
