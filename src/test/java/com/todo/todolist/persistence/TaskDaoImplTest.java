package com.todo.todolist.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskDaoImplTest {

    private final Integer TASK_ID = 1;
    private final String CONTENT = "Test task";

    @Mock
    private EntityManagerFactory entityManagerFactory;
    @Mock
    private EntityManager entityManager;
    @Mock
    private EntityTransaction entityTransaction;
    @Mock
    private TypedQuery<TaskEntity> query;

    @InjectMocks
    private TaskDaoImpl taskDao;

    @Test
    void addTask_Content_IdOfCreatedTaskIsReturned() {
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        doAnswer((Answer<Void>) invocation -> {
            TaskEntity taskEntity = invocation.getArgument(0);
            taskEntity.setId(TASK_ID);
            return null;
        }).when(entityManager).persist(any(TaskEntity.class));

        int resultId = taskDao.add(CONTENT);

        assertEquals(TASK_ID, resultId);
        verify(entityTransaction).begin();
        verify(entityTransaction).commit();
        verify(entityManager).close();
    }

    @Test
    void addTask_Exception_RollbackIsCalled() {
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.getTransaction().isActive()).thenReturn(true);
        doThrow(new RuntimeException("Test exception")).when(entityManager).persist(any(TaskEntity.class));

        assertThrows(RuntimeException.class, () -> taskDao.add(CONTENT));
        verify(entityTransaction).rollback();
        verify(entityTransaction, never()).commit();
        verify(entityManager).close();
    }

    @Test
    void addTask_Exception_TransactionNotActive_RollbackNotCalled() {
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.getTransaction().isActive()).thenReturn(false);
        doThrow(new RuntimeException("Test exception")).when(entityManager).persist(any(TaskEntity.class));

        assertThrows(RuntimeException.class, () -> taskDao.add(CONTENT));
        verify(entityTransaction, never()).rollback();
        verify(entityManager).close();
    }

    @Test
    void getTasks_TwoTasksInDb_ListOfTasksReturned() {
        TaskEntity task1 = new TaskEntity(1, "Task 1", false);
        TaskEntity task2 = new TaskEntity(2, "Task 2", true);
        List<TaskEntity> mockTasks = Arrays.asList(task1, task2);
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.createQuery("select t from TaskEntity t", TaskEntity.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(mockTasks);

        List<TaskEntity> result = taskDao.getTasks();

        assertEquals(mockTasks.size(), result.size());
        assertEquals(mockTasks, result);
        verify(entityTransaction).begin();
        verify(entityTransaction).commit();
        verify(entityManager).close();
    }

    @Test
    void getTasks_NoTasksInDb_EmptyListReturned() {
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.createQuery("select t from TaskEntity t", TaskEntity.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        List<TaskEntity> result = taskDao.getTasks();

        assertTrue(result.isEmpty());
        verify(entityTransaction).begin();
        verify(entityTransaction).commit();
        verify(entityManager).close();
    }

    @Test
    void updateStatus_ValidTaskId_StatusIsUpdated() {
        TaskEntity testedTask = new TaskEntity(TASK_ID, CONTENT, false);
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.find(TaskEntity.class, TASK_ID)).thenReturn(testedTask);

        boolean result = taskDao.updateStatus(TASK_ID, true);

        assertTrue(result);
        assertTrue(testedTask.isCompleted());
        verify(entityTransaction).begin();
        verify(entityTransaction).commit();
        verify(entityManager).close();
    }

    @Test
    void updateStatus_InvalidTaskId_StatusIsNotUpdated() {
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.find(TaskEntity.class, TASK_ID)).thenReturn(null);

        boolean result = taskDao.updateStatus(TASK_ID, true);

        assertFalse(result);
        verify(entityTransaction).begin();
        verify(entityManager).close();
    }

    @Test
    void updateStatus_Exception_RollbackIsCalled() {
        TaskEntity testedTask = new TaskEntity(TASK_ID, CONTENT, false);
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.find(TaskEntity.class, TASK_ID)).thenReturn(testedTask);
        when(entityManager.getTransaction().isActive()).thenReturn(true);
        doThrow(new RuntimeException("Test exception")).when(entityManager).merge(any(TaskEntity.class));

        assertThrows(RuntimeException.class, () -> taskDao.updateStatus(TASK_ID, true));
        verify(entityTransaction).rollback();
        verify(entityManager).close();
    }
}