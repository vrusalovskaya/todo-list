package com.todo.todolist.application;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService {
    private int counter = 0;
    private ArrayList<Task> tasks = new ArrayList<>();


    @Value("${task.default}")
    private String defaultTask;

    @PostConstruct
    public void loadDefaultTask() {
       add(defaultTask);
    }


    public int add(String content) {
        tasks.add(new Task(++counter, content));
        return counter;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public boolean updateStatus (int id, boolean completed){
        for (Task task : tasks) {
           if (Objects.equals(task.getId(), id)){
               task.setCompleted(completed);
               return true;
           }
        }
        return false;
    }

}
