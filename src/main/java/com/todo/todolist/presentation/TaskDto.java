package com.todo.todolist.presentation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude
public class TaskDto {
    private String content;
    private boolean isCompleted;
}
