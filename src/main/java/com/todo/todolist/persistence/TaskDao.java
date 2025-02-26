package com.todo.todolist.persistence;

import java.util.List;

public interface TaskDao {
    int createTask(String content);

    List<TaskEntity> getTasks();

    boolean updateTaskStatus(int id, boolean completed);
}
