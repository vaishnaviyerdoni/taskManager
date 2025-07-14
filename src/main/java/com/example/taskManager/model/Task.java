package com.example.taskManager.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity @Table (name="tasks") @Getter @Setter
public class Task {
    
    //fields

    @Id @GeneratedValue
    private int taskId;

    private String title;
    private String description;
    
    @Column(columnDefinition = "DATETIME2")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();

    //empty constructor
    public Task(){}

    //constructor
    public Task(int taskId, String title, String description, LocalDateTime createdAt){
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }
}
