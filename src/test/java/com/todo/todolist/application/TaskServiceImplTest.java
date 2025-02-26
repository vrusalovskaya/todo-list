package com.todo.todolist.application;

import com.todo.todolist.persistence.TaskDao;
import com.todo.todolist.persistence.TaskEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    private final Integer TASK_ID = 1;
    private final String CONTENT = "Test task";

    @Mock
    private TaskDao taskDao;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void loadDefaultTask_DefaultTaskAdded() {
        String defaultTask = "Default task";
        ReflectionTestUtils.setField(taskService, "defaultTask", defaultTask);

        taskService.loadDefaultTask();

        verify(taskDao).createTask(defaultTask);
    }

    @Test
    void addTask_Content_TaskCreatedWithGivenContent() {
        when(taskDao.createTask(CONTENT)).thenReturn(TASK_ID);

        int resultId = taskService.createTask(CONTENT);

        assertEquals(resultId, TASK_ID);
        verify(taskDao).createTask(CONTENT);
    }

    @Test
    void addTask_NullAsContent_ExceptionThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> taskService.createTask(null));

        assertEquals("No content provided for task creation", exception.getMessage());
    }

    @Test
    void addTask_EmptyContent_ExceptionThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> taskService.createTask(""));

        assertEquals("No content provided for task creation", exception.getMessage());
    }

    @Test
    void getTasks_TasksInTheDb_TasksReturned() {
        ArrayList<TaskEntity> taskEntities = new ArrayList<>();
        taskEntities.add(new TaskEntity(TASK_ID, CONTENT, false));
        taskEntities.add(new TaskEntity(2, "Second task", false));
        when(taskDao.getTasks()).thenReturn(taskEntities);

        var result = taskService.getTasks();

        assertEquals(result, taskEntities.stream().map(TaskMapper.INSTANCE::toModel).toList());
        verify(taskDao).getTasks();
    }

    @Test
    void getTasks_EmptyDb_EmptyListReturned() {
        when(taskDao.getTasks()).thenReturn(new ArrayList<>());

        var result = taskService.getTasks();

        assertTrue(result.isEmpty());
        verify(taskDao).getTasks();
    }

    @Test
    void updateStatus_StatusFalse_StatusOfTaskIsUpdatedToTrue() {
        when(taskDao.updateTaskStatus(TASK_ID, true)).thenReturn(true);

        boolean result = taskService.updateTaskStatus(TASK_ID, true);

        assertTrue(result);
        verify(taskDao).updateTaskStatus(TASK_ID, true);
    }

    @Test
    void updateStatus_InvalidTaskId_ReturnsFalse() {
        when(taskDao.updateTaskStatus(99, true)).thenReturn(false);

        boolean result = taskService.updateTaskStatus(99, true);

        assertFalse(result);
        verify(taskDao).updateTaskStatus(99, true);
    }
}