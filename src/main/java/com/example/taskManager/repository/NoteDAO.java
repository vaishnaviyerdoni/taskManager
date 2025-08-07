package com.example.taskManager.repository;

import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import org.slf4j.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.example.taskManager.model.Note;
import com.example.taskManager.model.Task;
import com.example.taskManager.model.User;

@Repository
public class NoteDAO{
    private static final Logger logger = LoggerFactory.getLogger(NoteDAO.class);
    private JdbcTemplate jdbc;

    //constructor
    public NoteDAO(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }
    
    //CREATE method: to add a note to a task
    public int addNote(Note note){
        String sql = "INSERT INTO notes (taskId, userId, content, createdAt) VALUES (?,?,?,?)";
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            PreparedStatementCreator psc = (Connection conn) -> {
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, note.getTask().getTaskId());
                stmt.setInt(2, note.getUser().getUserId());
                stmt.setString(3, note.getContent());
                stmt.setTimestamp(4, Timestamp.valueOf(note.getCreatedAt()));
                return stmt;
            };

            jdbc.update(psc, keyHolder);
            if(keyHolder.getKey() != null){
                @SuppressWarnings("null")
                int notesId = keyHolder.getKey().intValue();
                return notesId;
            }
            else{
                return 0;
            }
        }
        catch(Exception e){
            logger.error("Failed to add the note",e);
            e.printStackTrace();
            return -1;
        }
    }

    //READ method: to get the information about the note
    public List<Note> getNotebyTaskId(int taskId){
        String sql = "SELECT * FROM notes WHERE taskId = ?";
        try{
            return jdbc.query(sql, (org.springframework.jdbc.core.RowMapper<Note>)(rs, rowNum) -> {
                //foreign keys
                Task task = new Task();
                task.setTaskId(rs.getInt("taskId"));

                User user = new User();
                user.setUserId(rs.getInt("userId"));

                return new Note(
                    rs.getInt("notesId"),
                    task,
                    user,
                    rs.getString("content"),
                    rs.getTimestamp("createdAt").toLocalDateTime());
            }, taskId);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Failed to retrieve the data about notes", e);
            return Collections.emptyList();
        }
    }
    
    public List<Note> getNote(int notesId) {
        String sql = "SELECT * FROM notes WHERE notesId = ?";
        try{
            return jdbc.query(sql, (org.springframework.jdbc.core.RowMapper<Note>)(rs, rowNum) -> {
                //foreign key
                User user = new User();
                user.setUserId(rs.getInt("userId"));

                Task task = new Task();
                task.setTaskId(rs.getInt("taskId"));

                //other params
                return new Note(
                    rs.getInt("notesId"),
                    task,
                    user,
                    rs.getString("content"),
                    rs.getTimestamp("createdAt").toLocalDateTime());
            }, notesId);
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Failed to get note information", e);
            return Collections.emptyList();
        }
    }

    @SuppressWarnings("null")
    public boolean doesUserOwnNote(int userId, int taskId, int notesId) {
        String sql = "SELECT COUNT(*) FROM notes WHERE taskId = ? AND notesId = ? AND userId = ?";
        try{
            Integer count = jdbc.queryForObject(sql, Integer.class, taskId, notesId, userId);
            return count == 1;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //UPDATE method: to update the note for a specfic task
    public boolean updateNotes(int notesId, String content) {
        String sql = "UPDATE notes SET content = ? WHERE notesId = ?";
        try{
            PreparedStatementCreator psc = (Connection conn) -> {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, content);
                stmt.setInt(2, notesId);
                return stmt;
            };

            int rows = jdbc.update(psc);
            if(rows > 0){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Failed to update the note information", e);
            return false;
        }
    }

    //DELETE method : to delete the note for the notesId
    public boolean deleteNote(int notesId){
        String sql = "DELETE FROM notes WHERE notesId = ?";
        try{
            PreparedStatementCreator psc = (Connection conn) -> {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, notesId);
                return stmt;
            };

            int rows = jdbc.update(psc);
            if(rows > 0){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            logger.error("Failed to delete the note", e);
            e.printStackTrace();
            return false;
        }
    }
    
}