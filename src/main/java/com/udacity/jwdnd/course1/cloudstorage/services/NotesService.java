package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    private final NoteMapper noteMapper;

    public NotesService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int createNote(Note note) {

        if (note.getNoteid() != null)
            return noteMapper.update(note);

        return noteMapper.insert(note);
    }

    public List<Note> getNotes(Integer userid) {
        return noteMapper.getNotes(userid);
    }

    public Note getNote(Integer noteid) {
        return noteMapper.getNote(noteid);
    }

    public void delete(Integer noteid) {
        noteMapper.delete(noteid);
    }
}
