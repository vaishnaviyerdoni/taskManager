package com.example.taskManager.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.example.taskManager.model.User;
import java.sql.*;
import java.util.*;

@Repository
public class UserDAO {
    private final JdbcTemplate jdbc;
    //constructor
    public UserDAO(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    //CREATE method: to add/register user
    public int registerUser(User user) {
        try{
            String sql = "INSERT INTO Users (userName, email, password) VALUES (?,?,?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            PreparedStatementCreator psc = (Connection conn) -> {
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, user.getUserName());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getEmail());
                return stmt;
            };

            jdbc.update(psc, keyHolder);

            if(keyHolder.getKey()!= null){
                @SuppressWarnings("null")
                int userId = keyHolder.getKey().intValue();
                return userId;
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

    //READ methods: to get the information about tasks
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM Users";
        try{
            return jdbc.query(sql, (org.springframework.jdbc.core.RowMapper<User>) (rs, rowNum) ->
            new User(
                rs.getInt("userId"),
                rs.getString("userName"),
                rs.getString("email"),
                rs.getString("password")));
        }
        catch(Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public String getPasswordbyId(int userId) {
        String sql = "SELECT password WHERE userId = ?";
        try{
            String password = jdbc.queryForObject(sql, 
            (rs, rowNum) -> 
                rs.getString("password"),
                userId
            );

            return password;
        }catch(Exception e){
            e.printStackTrace();
            return "Failed to fetch password";
        }
    }

    public String getUsernameById(int userId) {
        String sql = "SELECT userName WHERE userId = ?";
        try{
            String userName = jdbc.queryForObject(sql,
                (rs, rowNum) -> 
                    rs.getString("userName"),
                    userId
            );

            return userName;
        }
        catch(Exception e){
            e.printStackTrace();
            return "Username not found";
        }
    }

    //UPDATE methods : to update the user Information
    
}
