package com.todo.todolist.presentation;

import com.todo.todolist.application.Task;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-11T22:43:28+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto toDto(Task task) {
        if ( task == null ) {
            return null;
        }

        int id = 0;
        String content = null;
        boolean completed = false;

        id = task.getId();
        content = task.getContent();
        completed = task.isCompleted();

        TaskDto taskDto = new TaskDto( id, content, completed );

        return taskDto;
    }
}
