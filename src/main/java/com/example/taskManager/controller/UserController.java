package com.example.taskManager.controller;

import com.example.taskManager.service.UserBusiness;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.example.taskManager.DTO.UserDTO.AddUser;
import com.example.taskManager.DTO.UserDTO.UserResponse;
import com.example.taskManager.model.User;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserBusiness userBusiness;

    public UserController(UserBusiness userBusiness){
        this.userBusiness = userBusiness;
    }

    @GetMapping("/user/{userId}")
    public List<User> getUser(@PathVariable int userId, @RequestParam String userName) {
        try{
            return userBusiness.getMyInfo(userId, userName);
        }
        catch(Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponse> registerUser(@RequestBody AddUser addUser) {
        try{
            int userId = userBusiness.addUsertoDb(addUser);
            UserResponse res = new UserResponse(userId, "User Resgistered Successfully");
            if (userId > 0){
                return ResponseEntity.ok(res);
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserResponse(-1, "Registration Failed"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserResponse(-1, "Registration Failed"));
        }
    }
    
}
