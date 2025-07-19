package com.example.taskManager.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.*;

@Service
public class TaskBusiness {
    private static final Logger logger = LoggerFactory.getLogger(TaskBusiness.class);
    private JdbcTemplate jdbc;

    public TaskBusiness(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    //Add Task to Task manager

    //Get all tasks

    //update the task Content(task Content is actual task body)

    //Delete the task

}
