package com.example.taskManager.repository;

import java.sql.*;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.example.taskManager.model.Task;
import com.example.taskManager.model.User;
import org.slf4j.*;

@Repository
public class TaskDAO {
    private final JdbcTemplate jdbc;
    private static final Logger logger = LoggerFactory.getLogger(TaskDAO.class);
    //constructor
    public TaskDAO(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    //CREATE method: to add Task 
    @SuppressWarnings("null")
    public int addTask(Task task){
        String sql = "INSERT INTO Tasks (userId, title, taskContent, createdAt) VALUES (?,?,?,?)";
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            PreparedStatementCreator psc = (Connection conn) -> {
                PreparedStatement smt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                smt.setInt(1, task.getUser().getUserId());
                smt.setString(2, task.getTitle());
                smt.setString(3,task.getTaskContent());
                smt.setTimestamp(4, Timestamp.valueOf(task.getCreatedAt()));
                return smt;
            };

            jdbc.update(psc, keyHolder);
            if(keyHolder.getKey() != null){
                @SuppressWarnings("null")
                int taskId = keyHolder.getKey().intValue();
                return taskId;
            }
            else{
                return -1;
            }
        }
        catch(Exception e){
            logger.error("Could not add Task", e);
            e.printStackTrace();
            return -1;
        }
    }

    //READ method: to fetch task information
    public List<Task> getAllTasks(int userId) {
        String sql = "SELECT * FROM Tasks WHERE userId = ?";
        try{    
            return jdbc.query(sql, (rs, rowNum) -> {
                //for foreign key
                User user = new User();
                user.setUserId(rs.getInt("userId"));

                //to get information
                 return new Task(
                    rs.getInt("taskId"),
                    user,
                    rs.getString("title"),
                    rs.getString("taskContent"),
                    rs.getTimestamp("createdAt").toLocalDateTime());
            }, userId);
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Could not fetch task information", e);
            return Collections.emptyList();
        }
    }

    public List<Task> getTaskbyID(int taskId){
        String sql = "SELECT * FROM Tasks WHERE taskId = ?";
        try{
            return jdbc.query(sql, (org.springframework.jdbc.core.RowMapper<Task>)(rs, rowNum) -> {
                //foreign keys
                User user = new User();
                user.setUserId(rs.getInt("userId"));

                return new Task(
                    rs.getInt("taskId"),
                    user,
                    rs.getString("title"),
                    rs.getString("taskContent"),
                    rs.getTimestamp("createdAt").toLocalDateTime());
            }, taskId);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Could not fetch the task information", e);
            return Collections.emptyList();
        }
    }

    public int getTaskIdbyuserId(int userId) {
        String sql = "SELECT taskId FROM tasks WHERE userId = ?";
        try{
            Integer taskId = jdbc.queryForObject(sql, 
            (rs, rowNum) -> rs.getInt("taskId"),
            userId
            );

            if(taskId != null){
                return taskId;
            }
            else{
                return 0;
            }
        }
        catch(Exception e){
            logger.error("Could not fetch the taskId ", e);
            e.printStackTrace();
            return -1;
        }
    }

    //UPDATE method : to update the task
    public boolean updateTask(String taskContent, int taskId) {
        String sql = "UPDATE tasks SET taskContent = ? WHERE userId = ?";
        try{
            PreparedStatementCreator psc = (Connection conn) -> {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, taskContent);
                stmt.setInt(2, taskId);
                return stmt;
            };

            int rows = jdbc.update(psc);
            if(rows > 0){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            logger.error("Failed to update the task", e);
            e.printStackTrace();
            return false;
        }
    }

    //DELETE method: to delete the task
    public boolean deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE taskId = ?";
        try{
            PreparedStatementCreator psc = (Connection conn) -> {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, taskId );
                return stmt;
            };

            int rows = jdbc.update(psc);
            if(rows > 0){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            logger.error("Failed to delete the task", e);
            e.printStackTrace();
            return false;
        }
    }
} 
