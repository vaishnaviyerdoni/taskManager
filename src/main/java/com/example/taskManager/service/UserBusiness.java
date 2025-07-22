package com.example.taskManager.service;

import org.slf4j.*;
import org.springframework.stereotype.Service;

import com.example.taskManager.DTO.UserDTO.AddUser;
import com.example.taskManager.DTO.UserDTO.Login;
import com.example.taskManager.Exceptions.InvalidPasswordException;
import com.example.taskManager.Exceptions.InvalidUserNameException;
import com.example.taskManager.model.User;
import com.example.taskManager.repository.UserDAO;
import java.util.*;

@Service
public class UserBusiness {
    private UserDAO userDAO;
    private static final Logger logger = LoggerFactory.getLogger(UserBusiness.class);

    public UserBusiness(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    //Register as User
    public int addUsertoDb(AddUser addUser) {
        try{
            String userName = addUser.getUserName();
            String email = addUser.getEmail();
            String password = addUser.getPassword();

            User user = new User(0, userName, email, password);
            int user_id = userDAO.registerUser(user);
            return user_id;
        }
        catch(Exception e){
            logger.error("User could not be registered!", e);
            e.printStackTrace();
            return -1;
        }
    }

    //Get your account information by your userId
    public List<User> getMyInfo(int userId, String userName) {
        List<User> users = new ArrayList<>();
            try{
            int user_id = userDAO.getUserIdbyUsername(userName);
            if (user_id == userId){
                users = userDAO.getUserInfo(userId);
            }
            return users;
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Could not fetch user info", e);
            return Collections.emptyList();
        }
    }

    //update the password and email
    public boolean updatePasscode(String userName, String password, int userId) {
        try{
            String user_name = userDAO.getUsernameById(userId);
            int userID = userDAO.getUserIdbyUsername(user_name);
            if(user_name.equals(userName) && userID == userId){
                return userDAO.updatePasscode(userId, userName, password);
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Could not update password", e);
            return false;
        }
    }

    public boolean updateUserEmail(String userName, String email, int userId) {
        try{
            String user_name = userDAO.getUsernameById(userId);
            int userID = userDAO.getUserIdbyUsername(userName);
            if (user_name.equals(userName) && userID == userId){
                return userDAO.updateEmail(userID, user_name, email);
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Could not update Email", e);
            return false;
        }
    }

    //delete the account
    public boolean deleteMyaccount(int userId, String userName, String password) {
        try{
            String user_name = userDAO.getUsernameById(userId);
            int userID = userDAO.getUserIdbyUsername(userName);
            String passcode = userDAO.getPasswordbyId(userID);
            
            if(userName.equals(user_name) && userID == userId && passcode.equals(password)){
                return userDAO.deleteAccount(userId);
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Failed to delete Account", e);
            return false;
        }
    }

    //user login authentication
    public boolean isValidUser(Login login) {
        try{
            int userId = login.getUserId();
            String userName = login.getUserName();
            String password = login.getPassword();
            String email = login.getEmail();

            String passcode = userDAO.getPasswordbyId(userId);
            int userID = userDAO.getUserIdbyUsername(userName);
            String user_name = userDAO.getUsernameById(userID);
            String Email = userDAO.getEmail(userID);

            System.out.println("Input username: " + user_name);
            System.out.println("DB username: " + userName);

            System.out.println("Input passcode: " + passcode);
            System.out.println("DB passcode: " + password);

            return(passcode.equals(password) && email.equals(Email) && userName.equals(user_name) && userID == userId);
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Invalid login, Check your credentials again", e);
            return false;
        }
   }

    //password and email validation
    public boolean isValiduserName(String username){
        try{
            if (username.length() < 5 || username.length() > 15){
                throw new InvalidUserNameException("UserName should from 5 to 15 letters!");
            }

            if (username.matches("^[a-zA-Z0-9_]{5,15}$")){
                return true;
            }

            return false;
        }
        catch(InvalidUserNameException e){
            e.getMessage();
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Invalid username, user another one", e);
            return false;
        }
    }

    public boolean isValidPasscode(String passcode) {
        try{
            if (passcode.length() < 8 || passcode.length() > 15){
                throw new InvalidPasswordException("The password should have between 8 to 15 letters");
            }
    
            if(passcode.matches("^[a-zA-Z0-9!@#$%&_*]{8,15}$")){
                return true;
            }

            return false;
        }
        catch(InvalidPasswordException e){
            e.getMessage();
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Invalid password, user another one", e);
            return false;
        }
    }
}