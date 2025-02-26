package com.todo.todolist.application;

import com.todo.todolist.persistence.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    Task toModel(TaskEntity taskEntity);
}
