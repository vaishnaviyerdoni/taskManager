package com.example.taskManager.service;

import org.springframework.stereotype.Service;

import com.example.taskManager.DTO.NoteDTO.AddNote;
import com.example.taskManager.DTO.NoteDTO.UpdateNote;
import com.example.taskManager.Exceptions.UserNotfoundException;
import com.example.taskManager.model.Note;
import com.example.taskManager.model.Task;
import com.example.taskManager.model.User;
import com.example.taskManager.repository.NoteDAO;
import com.example.taskManager.repository.TaskDAO;
import com.example.taskManager.repository.UserDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.*;

@Service
public class NoteBusiness {
    private static final Logger logger = LoggerFactory.getLogger(NoteBusiness.class);
    TaskDAO taskDAO;
    UserDAO userDAO;
    NoteDAO noteDAO;

    public NoteBusiness(TaskDAO taskDAO, UserDAO userDAO, NoteDAO noteDAO){
        this.userDAO = userDAO;
        this.taskDAO = taskDAO;
        this.noteDAO = noteDAO;
    }

    //Add a note to task
    public int addNoteMytask(AddNote note, String userName) {
        try{
            int userId = note.getUserId();
            int taskId = note.getTaskId();
            String content = note.getContent();
            LocalDate createdAtDate = note.getCreatedAt();

            LocalDateTime createdAt = createdAtDate.atStartOfDay();

            int userID = userDAO.getUserIdbyUsername(userName);
            int taskID = taskDAO.getTaskIdbyuserId(userID);

            if(taskId == taskID && userID == userId) {
                Task task = new Task();
                task.setTaskId(taskID);

                User user = new User();
                user.setUserId(userID);

                Note MyNote = new Note(0, task, user, content, createdAt);
                return noteDAO.addNote(MyNote);
            }
            else{
                throw new UserNotfoundException("User for the given userName not found");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Failed to add note", e);
            return -1;
        }
    }

    //for a given task, get all its notes
    public List<Note> getMyNotes(int userId, int taskId, String userName){
        List<Note> notes = new ArrayList<>();
        try{
            int userID = userDAO.getUserIdbyUsername(userName);
            int taskID = taskDAO.getTaskIdbyuserId(userID);
            if(userID == userId && taskID == taskId){
                notes = noteDAO.getNotebyTaskId(taskID);
                return notes;
            }
            else{
                throw new UserNotfoundException("User for given user name was found");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Error occurred when fetching information", e);
            return Collections.emptyList();
        }
    }

    //update the note for that taskId
    public boolean updateMyNote(UpdateNote noteContent, int notesId){
        try{
            String userName = noteContent.getUserName();
            String content = noteContent.getContent();
            int taskId = noteContent.getTaskId();
            int userId = noteContent.getUserId();

            
            boolean owns = noteDAO.doesUserOwnNote(userId, taskId, notesId);
            if(owns){
                return noteDAO.updateNotes(notesId, content);
            }
            else{
                throw new UserNotfoundException("The note does not belong to given user");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Error occurred when updating note", e);
            return false;
        }
    }

    //delete a note for the task
    public boolean deleteMyNote(int userId, int taskId, String userName, int notesId){
        try{
            boolean owns = noteDAO.doesUserOwnNote(userId, taskId, notesId);
            if(owns){
                return noteDAO.deleteNote(notesId);
            }
            else{
                throw new UserNotfoundException("The note does not belong to given user");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Error occurred while deleting note", e);
            return false;
        }
    }
}
