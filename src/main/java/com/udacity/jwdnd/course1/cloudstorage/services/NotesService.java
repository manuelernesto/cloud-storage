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

    public void createNote(Note note) {

        if (note.getNoteid() != null)
            noteMapper.update(note);
        else
            noteMapper.insert(note);
    }

    public List<Note> getNotes(Integer userid) {
        return noteMapper.getNotes(userid);
    }


    public void delete(Integer noteid) {
        noteMapper.delete(noteid);
    }
}
