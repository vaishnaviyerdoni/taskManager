package com.example.taskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.taskManager.model.Task;

public interface NoteDAO extends JpaRepository<Task, Integer>{

    
}