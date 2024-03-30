package com.sergiosantiago.conectados.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sergiosantiago.conectados.Utils.Errors;
import com.sergiosantiago.conectados.Utils.Messages;
import com.sergiosantiago.conectados.config.CustomMapper;
import com.sergiosantiago.conectados.dtos.NoteDTO;
import com.sergiosantiago.conectados.models.Note;
import com.sergiosantiago.conectados.models.Room;
import com.sergiosantiago.conectados.models.User;
import com.sergiosantiago.conectados.models.auth.HttpResponse;
import com.sergiosantiago.conectados.repository.NoteRepository;
import com.sergiosantiago.conectados.services.base.BaseServiceImpl;

@Service
public class NoteService extends BaseServiceImpl<Note, Long, NoteRepository> {

    private RoomService roomService;
    private UserService userService;

    public NoteService(NoteRepository noteRepository, CustomMapper modelMapper, UserService userService) {
        super(noteRepository, modelMapper);
        this.userService = userService;
    }

    public HttpResponse<NoteDTO> createNote(User user, NoteDTO noteDTO) {
        Room room = roomService.findById(noteDTO.getRoomId());
        HttpResponse<NoteDTO> res = new HttpResponse<>();
        res.setMessage(Errors.notWork);
        res.setCode(400L);
        if (room != null) {
            Note note = modelMapper.map(noteDTO, Note.class);
            note.setWriter(user);
            note.setWriteIn(room);
            this.save(note);
            res.setCode(200L);
            res.setMessage(Messages.sucefull);
        }
        return res;
    }

    public HttpResponse<NoteDTO> deleteNote(User user, NoteDTO noteDTO) {
        Room room = roomService.findById(noteDTO.getRoomId());
        HttpResponse<NoteDTO> res = new HttpResponse<>();
        res.setMessage(Errors.notYourSelf);
        res.setCode(400L);
        Note note = this.findById(noteDTO.getId());
        if (room != null && room.getAllMembers().contains(user) && note != null) {
            room.getNotes().remove(note);
            User writer = userService.findById(note.getWriter().getId());
            writer.getNotes().remove(note);
            this.delete(note);
            res.setCode(200L);
            res.setMessage(Messages.sucefull);
        }
        return res;

    }

    public HttpResponse<List<NoteDTO>> getAllNotesByRoom(Long id) {
        HttpResponse<List<NoteDTO>> res = new HttpResponse<>();
        List<Note> notes = repository.findByRoomId(id);
        res.setCode(200L);
        res.setMessage(Messages.sucefull);
        res.setData(notes.parallelStream().map(n -> modelMapper.map(n, NoteDTO.class)).collect(Collectors.toList()));
        return res;
    }

}
