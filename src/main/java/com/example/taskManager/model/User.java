package com.example.taskManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name="users") @Getter @Setter
public class User {
    
    //fields

    @Id @GeneratedValue
    private int userId;

    private String userName;
    private String password;

    //empty constructor
    public User(){}
    
    //constructor
    public User(int userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
}
