package com.example.taskManager.DTO.NoteDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter

public class UpdateNote {
    private int userId;
    private int taskId;
    private String userName;
    private String content;
}
