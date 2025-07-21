package com.example.taskManager.controller;

import com.example.taskManager.service.UserBusiness;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.example.taskManager.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
}
