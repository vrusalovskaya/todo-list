package com.todo.todolist.application;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private ArrayList<Task> tasks = new ArrayList<>();

    public void add(Task task){
        tasks.add(task);
    }

    public List<Task> getTasks(){
        return new ArrayList<>(tasks);
    }


}
