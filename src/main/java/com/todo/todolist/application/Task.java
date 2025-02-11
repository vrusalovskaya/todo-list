package com.todo.todolist.application;

import lombok.Data;

@Data
public class Task {
    private String content;
    private boolean isCompleted;

    public Task(String content) {
        this.content = content;
    }
}
