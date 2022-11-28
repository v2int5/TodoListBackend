package com.todolist.service;

import com.todolist.model.Task;
import com.todolist.model.User;
import com.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskrepository;



    @Autowired
    public TaskService(TaskRepository taskrepository) {
        this.taskrepository = taskrepository;

    }



    public Task findTaskById(Long id) {
        return taskrepository.findTaskById(id);
    }


    public Task addTask(Task task, User user){

        task.setUser(user);

        return taskrepository.save(task);
    }

    public Task moveTask(Task task){
        return taskrepository.save(task);
    }

    public List<Task> findAllTasks() {
        return taskrepository.findAll();
    }

    public List<Task> findTaskByDate(Date date) {
        return taskrepository.findTasksByDate(date);
    }

    public List<Task> findTasksByUser(User user) {
        return taskrepository.findTasksByUser(user);
    }
    public List<Task> findTasksByEvent(String event) {
        return taskrepository.findTasksByEvent(event);
    }

    @Transactional
    public void deleteTask(Long id) throws IOException {
        taskrepository.deleteTaskById(id);
    }



}