package com.todo.todolist.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class TaskDaoImpl implements TaskDao {

    private static final Logger logger = LogManager.getLogger(TaskDaoImpl.class);

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
            logger.info(String.format("Task with \" %s \" content was saved to the database", content));

            return taskEntity.getId();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                logger.info("Saving transaction was rolled back");
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<TaskEntity> getTasks() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            List<TaskEntity> taskEntities = entityManager.createQuery("select t from TaskEntity t", TaskEntity.class).getResultList();

            entityManager.getTransaction().commit();
            logger.info("List of tasks was retrieved from the database");
            return taskEntities;
        }
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
            logger.info(String.format("The status of the task with id \" %s \" was changed to \" %s \"", id, completed));

            return true;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                logger.info("Updating transaction was rolled back");
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
