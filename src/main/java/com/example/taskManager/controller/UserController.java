package com.example.taskManager.controller;

import com.example.taskManager.service.UserBusiness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.example.taskManager.DTO.UserDTO.*;
import com.example.taskManager.model.User;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

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

    @PostMapping("/user/register")
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

    @PostMapping("/user/login")
    public ResponseEntity<String> loginValidation(@RequestBody Login login) {
        try{
            boolean isValid = userBusiness.isValidUser(login);
            if(isValid){
                return ResponseEntity.ok("User logged in Successfully!");
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to login,check the credentials");
            }
        }
       catch(Exception e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to login,check the credentials");
       }
    }
    
    @PutMapping("user/{userId}/email")
    public ResponseEntity<String> updateMyEmail(@PathVariable int userId, @RequestBody EmailUpdate email) {
        try{
            boolean isUpdated = userBusiness.updateUserEmail(userId, email);
            if(isUpdated){
                return ResponseEntity.ok("Email Updated!");
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Email!");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Email!");
        }
    }

    @PutMapping("user/{userId}/password")
    public ResponseEntity<String> updateMypassword(@PathVariable int userId, @RequestBody PasswordUpdate password) {
        try{
            boolean isUpdated = userBusiness.updatePasscode(userId, password);
            if(isUpdated){
                return ResponseEntity.ok("Password updated!");
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Password");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Password");
        }
    }
    
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteAccount(@PathVariable int userId, @RequestParam String userName, @RequestParam String password){
        try{
            boolean isDeleted = userBusiness.deleteMyaccount(userId, userName, password);
            if(isDeleted){
                return ResponseEntity.ok("Account Deleted");
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Deleted");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Delete Account!");
        }
    }
}
