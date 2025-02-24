package com.todo.todolist.application;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Task {
    private int id;
    private String content;
    private boolean completed;

    public Task(String content) {
        this.content = content;
    }

    public Task(int id, String content) {
        this.id = id;
        this.content = content;
    }
}
