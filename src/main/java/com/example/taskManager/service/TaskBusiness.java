package com.example.taskManager.service;

import org.springframework.stereotype.Service;

import com.example.taskManager.DTO.TaskDTO.AddTask;
import com.example.taskManager.DTO.TaskDTO.TaskUpdate;
import com.example.taskManager.Exceptions.UserNotfoundException;
import com.example.taskManager.model.Task;
import com.example.taskManager.model.User;
import com.example.taskManager.repository.TaskDAO;
import com.example.taskManager.repository.UserDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;
import org.slf4j.*;

@Service
public class TaskBusiness {
    private static final Logger logger = LoggerFactory.getLogger(TaskBusiness.class);
    private UserDAO userDAO;
    private TaskDAO taskDAO;

    public TaskBusiness(UserDAO userDAO, TaskDAO taskDAO){
        this.userDAO = userDAO;
        this.taskDAO = taskDAO;
    }

    //Add Task to Task manager
    public int addTasktoManager(AddTask tasks) {
        int userID = 0;
        int taskID = 0;
        try{
            int userId = tasks.getUserId();
            String userName = tasks.getUserName();
            String title = tasks.getTitle();
            String taskContent = tasks.getTaskContent();
            LocalDateTime createdAt = tasks.getCreatedAt();

            userID = userDAO.getUserIdbyUsername(userName);
            User user = new User();
            if(userID == userId){
                user.setUserId(userID);
                Task task = new Task(taskID, user, title, taskContent, createdAt);
                taskID = taskDAO.addTask(task);
                return taskID;
            }
            else{
                throw new UserNotfoundException("user for given username not found!");
            }  
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Could not add Task", e);
            return -1;
        }
    }

    //Get all tasks
    public List<Task> getMyTasks(int userId, String userName) {
        List<Task> tasks = new ArrayList<>();
        try{
            int userID = userDAO.getUserIdbyUsername(userName);
            if(userId == userID){
                tasks = taskDAO.getAllTasks(userID);
                return tasks;
            }
            else{
                throw new UserNotfoundException("User for given userID not found");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Error while fetching task information", e);
            return Collections.emptyList();
        }
    }

    public List<Task> getTaskbyId(int taskId, int userId, String userName){
        List<Task> tasks = new ArrayList<>();
        try{
            int userID = userDAO.getUserIdbyUsername(userName);
            int taskID = taskDAO.getTaskIdbyuserId(userID);

            if(taskID == taskId && userID == userId){
                tasks = taskDAO.getTaskbyID(taskID);
                return tasks;
            }
            else{
                throw new UserNotfoundException("User for the given userID not found");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("User for given userName not found", e);
            return Collections.emptyList();
        }
    }

    //update the task Content(task Content is actual task body)
    public boolean updateTask(int taskId, TaskUpdate task){
        try{
            String userName = task.getUserName();
            int userId = task.getUserId();
            String newContent = task.getNContent();

            int userID = userDAO.getUserIdbyUsername(userName);
            int taskID = taskDAO.getTaskIdbyuserId(userID);

            if(taskId == taskID && userID == userId){
                boolean isUpdated = taskDAO.updateTask(newContent, taskID);
                return isUpdated;
            }
            else{
                throw new UserNotfoundException("User for the given userID not found");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Error while updating task", e);
            return false;
        }
    }

    //Delete the task
    public boolean deleteMytask(int taskId, int userId, String userName){
        try{
            int userID = userDAO.getUserIdbyUsername(userName);
            int taskID = taskDAO.getTaskIdbyuserId(userID);

            if(taskId == taskID && userID == userId){
                boolean isDeleted = taskDAO.deleteTask(taskID);
                return isDeleted;
            }
            else{
                throw new UserNotfoundException("User for the given userID not found");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Error while deleting task", e);
            return false;
        }
    }

}
