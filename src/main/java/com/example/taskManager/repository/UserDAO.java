package com.example.taskManager.repository;

import org.slf4j.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.example.taskManager.Exceptions.UserNotfoundException;
import com.example.taskManager.model.User;
import java.sql.*;
import java.util.*;

@Repository
public class UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
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
            logger.error("Failed to register the User", e);
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
            logger.error("Failed to retrieve all the user information", e);
            return Collections.emptyList();
        } 
    }

    public String getPasswordbyId(int userId) {
        String sql = "SELECT password FROM users WHERE userId = ?";
        try{
            String password = jdbc.queryForObject(sql, 
            (rs, rowNum) -> 
                rs.getString("password"),
                userId
            );

            return password;
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Failed to fetch the errors", e);
            return "Failed to fetch password";
        }
    }

    public String getUsernameById(int userId) {
        String sql = "SELECT userName FROM users WHERE userId = ?";
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
            logger.error("Failed to fetch the username", e);
            return "Username not found";
        }
    }

    //UPDATE methods : to update the user Information
    public boolean updatePasscode(int userId, String user_name, String newPassword){
        String sql = "UPDATE Users SET password = ? WHERE userId = ?";
        try{
            String name = getUsernameById(userId);
            if(name.equals(user_name)){
                    PreparedStatementCreator psc = (Connection conn) -> {
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, newPassword);
                    stmt.setInt(2, userId);
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
            else{
                throw new UserNotfoundException("User not found for given username!");
            }
        }
        catch(Exception e){
            logger.error("Failed to update the passcode", e);
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEmail(int userId, String user_name, String nEmail) {
        String sql = "UPDATE Users SET email = ? WHERE userId = ?";
        try{ 
            String name = getUsernameById(userId);
            if(name.equals(user_name)){
                PreparedStatementCreator psc = (Connection conn) -> {
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, nEmail);
                    stmt.setInt(2, userId);
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
            else{
                throw new UserNotfoundException("User for given username not found!");    
            }
        }
        catch(Exception e){
            logger.error("Failed to update the Email", e);
            e.printStackTrace();
            return false;
        }
    }

    //DELETE method : for user to delete their account
    public boolean deleteAccount(int userId){
        String sql = "DELETE FROM Users WHERE userId = ?";
        try{
            PreparedStatementCreator psc = (Connection conn) -> {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, userId);
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
            logger.error("Failed to delete the user information", e);
            e.printStackTrace();
            return false;
        }
    }
}
