package com.example.taskManager.DTO.TaskDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class TaskResponse {
    private int taskId;
    private String message;
}
