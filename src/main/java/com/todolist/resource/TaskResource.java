package com.todolist.resource;

import com.todolist.model.Task;
import com.todolist.model.User;
import com.todolist.service.TaskService;
import com.todolist.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping(path = "/api/task")
public class TaskResource {

    private final TaskService taskService;
    private final UserService userService;

    public TaskResource(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> tasks = taskService.findAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Task> addTask(@RequestBody Task task){
        try {
            User user = userService.getCurrentUser();
            task.setId(null);
            Task newTask = taskService.addTask(task, user);
            System.out.println("addTask");
            return new ResponseEntity<>(newTask, HttpStatus.CREATED);

        }catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id){
        Task task = taskService.findTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/findByEvent/{event}")
    public ResponseEntity<List<Task>> getTasksByEvent(@PathVariable("event") String event){
        List<Task> taskList = taskService.findTasksByEvent(event);
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Task> updateTask(@RequestBody Task task){
        User user = userService.getCurrentUser();
        Task updatePost = taskService.addTask(task, user);
        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }

    @PutMapping("/moveTask")
    public ResponseEntity<Task> moveTaskById(@RequestBody Task task){
        Task newTask = taskService.moveTask(task);
        return new ResponseEntity<>(newTask, HttpStatus.OK); //TODO:make that moving tasks does not make user "null".
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<Task> completeTaskById(@PathVariable Long id){
        Task task = taskService.findTaskById(id);
        task.setComplete(!task.isComplete());
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long id) throws IOException {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/user/{id}/tasks")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable("id") Long id){
        User user = userService.getCurrentUser();
        List<Task> tasks = taskService.findTasksByUser(user);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


}