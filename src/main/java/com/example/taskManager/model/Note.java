package com.example.taskManager.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "notes") @Getter @Setter
public class Note {
    
    //Fields
    
    @Id @GeneratedValue
    private int notesId;

    @ManyToOne @JoinColumn(name="taskId")
    private Task task;

    private String content;
    
    @Column(columnDefinition = "DATETIME2")
    private LocalDateTime createdAt;
    
    //empty constructor
    public Note(){}

    //constructor
    public Note(int notesId, Task task, String content, LocalDateTime createdAt){
        this.notesId = notesId;
        this.task = task;
        this.content = content;
        this.createdAt = createdAt;
    }
}
