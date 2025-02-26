package com.todo.todolist.presentation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDto {
    private int id;
    private String content;
    private boolean completed;
}
