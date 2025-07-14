package com.example.taskManager.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.taskManager.model.Note;

@Repository
public class NoteDAO{

    /* 
    private Note note;

    //constructor
    public NoteDAO(Note note){
        this.note = note;
    }
        */

    //to get all notes by taskId
    public List<Note> getNotebyTasId(int TaskId) {

        List<Note> notes = new ArrayList<>();
        try{

        }
        catch(Exception e){}
        return notes;
    }
}