package com.example.taskManager.DTO.TaskDTO;

import java.time.LocalDate;
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
    private LocalDate createdAt;
    private String userName;
}
