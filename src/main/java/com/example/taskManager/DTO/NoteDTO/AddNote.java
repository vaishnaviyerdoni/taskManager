package com.example.taskManager.DTO.NoteDTO;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter

public class AddNote {
    
    private int taskId;
    private int userId;
    private String content;
    private LocalDate createdAt;
}
