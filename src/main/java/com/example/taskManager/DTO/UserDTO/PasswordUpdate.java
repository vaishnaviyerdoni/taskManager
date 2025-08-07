package com.example.taskManager.DTO.UserDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter

public class PasswordUpdate {
    private String userName;
    private String password;
}
