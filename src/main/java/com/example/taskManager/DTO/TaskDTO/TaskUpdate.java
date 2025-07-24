package com.example.taskManager.DTO.TaskDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter@Getter

public class TaskUpdate {
    private String taskContent;
    private String userName;
    private int userId;
}
