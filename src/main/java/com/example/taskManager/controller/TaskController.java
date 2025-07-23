package com.example.taskManager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.taskManager.service.TaskBusiness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;
import com.example.taskManager.DTO.TaskDTO.AddTask;
import com.example.taskManager.DTO.TaskDTO.TaskResponse;
import com.example.taskManager.DTO.TaskDTO.TaskUpdate;
import com.example.taskManager.model.Task;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api")
public class TaskController {
    private TaskBusiness taskBusiness;

    public TaskController(TaskBusiness taskBusiness){
        this.taskBusiness = taskBusiness;
    }

    @GetMapping("/task/{userId}")
    public List<Task> getTask(@PathVariable int userId, @RequestParam String userName) {
        try{
            return taskBusiness.getMyTasks(userId, userName);
        }
        catch(Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @PostMapping("/task/addtask")
    public ResponseEntity<TaskResponse> addmyTask(@RequestBody AddTask task) {
        try{
            int taskId = taskBusiness.addTasktoManager(task);
            TaskResponse res = new TaskResponse(taskId, "Task Added!");
            if(taskId > 0){
                return ResponseEntity.ok(res);
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TaskResponse(-1, "Failed to add task"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TaskResponse(-1, e.getMessage()));
        }    
    }
    
    @PutMapping("/task/{taskId}")
    public ResponseEntity<String> putMethodName(@PathVariable int taskId, @RequestBody TaskUpdate task) {
        try{
            boolean isUpdated = taskBusiness.updateTask(taskId, task);
            if(isUpdated){
                return ResponseEntity.ok("Task updated");
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update task");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/task/{taskId}")
    public ResponseEntity<String> deleteMytask(@PathVariable int taskId, @RequestParam int userId, @RequestParam String userName){
        try{
            boolean isDeleted = taskBusiness.deleteMytask(taskId, userId, userName);
            if(isDeleted){
                return ResponseEntity.ok("Deleted task");
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete task");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
}
