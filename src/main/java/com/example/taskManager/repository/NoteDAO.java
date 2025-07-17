package com.example.taskManager.repository;

import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
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
    private JdbcTemplate jdbc;

    //constructor
    public NoteDAO(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }
    
    //CREATE method: to add a note to a task
    public int addNote(int taskId, Note note){
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
            return Collections.emptyList();
        }
    }
    
    public List<Note> getNote(int notesId) {
        String sql = "";
    }
}