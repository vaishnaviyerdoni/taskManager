package com.example.taskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.taskManager.model.Task;

public interface TaskDAO extends JpaRepository<Task, Integer> {

    
} 
