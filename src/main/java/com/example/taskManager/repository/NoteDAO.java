package com.example.taskManager.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.taskManager.model.Note;

@Repository
public class NoteDAO{
    private JdbcTemplate jdbc;

    //constructor
    public NoteDAO(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }
        

    //to get all notes by taskId
    public List<Note> getNotebyTaskId(int TaskId) {

        List<Note> notes = new ArrayList<>();
        try{

        }
        catch(Exception e){}
        return notes;
    }
}