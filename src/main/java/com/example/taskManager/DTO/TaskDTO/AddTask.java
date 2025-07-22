package com.example.taskManager.DTO.TaskDTO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter

public class AddTask {
    private int userId;
    private String title;
    private String taskContent;
    private LocalDateTime createdAt;
    private String userName;
}
