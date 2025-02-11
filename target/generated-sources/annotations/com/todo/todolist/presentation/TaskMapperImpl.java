package com.todo.todolist.presentation;

import com.todo.todolist.application.Task;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-11T11:42:22+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto toDto(Task task) {
        if ( task == null ) {
            return null;
        }

        String content = null;

        content = task.getContent();

        boolean isCompleted = false;

        TaskDto taskDto = new TaskDto( content, isCompleted );

        taskDto.setCompleted( task.isCompleted() );

        return taskDto;
    }
}
