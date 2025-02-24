package com.todo.todolist.persistence;

import java.util.List;

public interface TaskDao {
    int add(String content);

    List<TaskEntity> getTasks();

    boolean updateStatus(int id, boolean completed);
}
