package com.todo.todolist.presentation;

import com.todo.todolist.application.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskDtoMapper {
    TaskDtoMapper INSTANCE = Mappers.getMapper(TaskDtoMapper.class);

    TaskDto toDto(Task task);
}
