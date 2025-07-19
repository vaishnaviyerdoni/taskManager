package com.example.taskManager.service;

import org.springframework.stereotype.Service;
import com.example.taskManager.Exceptions.UserNotfoundException;
import com.example.taskManager.model.Note;
import com.example.taskManager.model.Task;
import com.example.taskManager.model.User;
import com.example.taskManager.repository.NoteDAO;
import com.example.taskManager.repository.TaskDAO;
import com.example.taskManager.repository.UserDAO;
import java.time.LocalDateTime;
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
    public int addNoteMytask(int notesId, int taskId, int userId, String content, LocalDateTime createdAt, String userName) {
        try{
            int userID = userDAO.getUserIdbyUsername(userName);
            int taskID = taskDAO.getTaskIdbyuserId(userID);

            if(taskId == taskID && userID == userId) {
                Task task = new Task();
                task.setTaskId(taskID);

                User user = new User();
                user.setUserId(userID);

                Note note = new Note(notesId, task, user, content, createdAt);
                int notesID = noteDAO.addNote(taskID, note);
                return notesId;
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

    //update the note for that taskId

    //delete a note for the task
}
