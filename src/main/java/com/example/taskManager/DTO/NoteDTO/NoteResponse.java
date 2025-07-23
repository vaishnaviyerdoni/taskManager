package com.example.taskManager.DTO.NoteDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter

public class NoteResponse {
    private int notesId;
    private String message;
}
