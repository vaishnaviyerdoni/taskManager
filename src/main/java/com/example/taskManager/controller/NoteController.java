package com.example.taskManager.controller;

import org.springframework.web.bind.annotation.RestController;
import com.example.taskManager.DTO.NoteDTO.AddNote;
import com.example.taskManager.DTO.NoteDTO.NoteResponse;
import com.example.taskManager.DTO.NoteDTO.UpdateNote;
import com.example.taskManager.model.Note;
import com.example.taskManager.service.NoteBusiness;
import java.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RequestMapping("/api")
@RestController

public class NoteController {
    private NoteBusiness noteBusiness;

    public NoteController(NoteBusiness noteBusiness){
        this.noteBusiness = noteBusiness;
    }

    @GetMapping("/note/{taskId}")
    public List<Note> getMytaskNotes(@PathVariable int taskId, @RequestParam int userId, @RequestParam String userName) {
        try{
            return noteBusiness.getMyNotes(userId, taskId, userName);
        }
        catch(Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @PostMapping("/note")
    public ResponseEntity<NoteResponse> addNoteToTask(@RequestParam String userName, @RequestBody AddNote note) {
        NoteResponse res = new NoteResponse();
        try{
            int notesId = noteBusiness.addNoteMytask(note, userName);
            res.setNotesId(notesId);
            res.setMessage("Note Added successfully");
            return ResponseEntity.ok(res);
        }
        catch(Exception e){
            e.printStackTrace();
            res.setNotesId(-1);
            res.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
    
    @PutMapping("note/{notesId}")
    public ResponseEntity<String> updateMyNote(@PathVariable int notesId, @RequestBody UpdateNote NoteContent) {
        try{
            boolean isUpdated = noteBusiness.updateMyNote(NoteContent, notesId);
            if(isUpdated){
                return ResponseEntity.ok("Note Updated!");
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Note");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/note/{notesId}")
    public ResponseEntity<String> deleteMyNote(@PathVariable int notesId, @RequestParam int userId, @RequestParam int taskId, @RequestParam String userName){
        try{
            boolean isDeleted = noteBusiness.deleteMyNote(userId, taskId, userName, notesId);
            if(isDeleted){
                return ResponseEntity.ok("Deleted Note");
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete note");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }   
    
}
