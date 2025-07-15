package com.example.taskManager.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.example.taskManager.model.User;
import java.sql.*;

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
                return stmt;
            };

            jdbc.update(sql, keyHolder);

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
}
