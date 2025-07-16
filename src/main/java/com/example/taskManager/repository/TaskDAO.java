package com.example.taskManager.repository;

import java.sql.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.example.taskManager.model.Task;

@Repository
public class TaskDAO {
    private final JdbcTemplate jdbc;
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
            e.printStackTrace();
            return -1;
        }
    }
} 
