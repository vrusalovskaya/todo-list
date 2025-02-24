package com.todo.todolist.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class TaskDaoImpl implements TaskDao {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public int add(String content) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setContent(content);

            entityManager.persist(taskEntity);

            entityManager.getTransaction().commit();

            return taskEntity.getId();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<TaskEntity> getTasks() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<TaskEntity> taskEntities = entityManager.createQuery("select t from TaskEntity t", TaskEntity.class).getResultList();

        entityManager.getTransaction().commit();
        return taskEntities;
    }

    @Override
    public boolean updateStatus(int id, boolean completed) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            TaskEntity taskEntity = entityManager.find(TaskEntity.class, id);

            if (taskEntity == null) {
                return false;
            }

            taskEntity.setCompleted(completed);

            entityManager.merge(taskEntity);

            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
