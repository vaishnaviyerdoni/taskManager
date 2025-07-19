package com.example.taskManager.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.*;

@Service
public class NoteBusiness {
    private static final Logger logger = LoggerFactory.getLogger(NoteBusiness.class);
    private JdbcTemplate jdbc;

    public NoteBusiness(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    //Add a note to task

    //for a given task, get all its notes

    //update the note for that taskId

    //delete a note for the task
}
