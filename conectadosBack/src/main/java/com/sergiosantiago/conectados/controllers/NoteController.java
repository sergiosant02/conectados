package com.sergiosantiago.conectados.controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sergiosantiago.conectados.Utils.Utils;
import com.sergiosantiago.conectados.dtos.NoteDTO;
import com.sergiosantiago.conectados.models.User;
import com.sergiosantiago.conectados.models.auth.HttpResponse;
import com.sergiosantiago.conectados.services.NoteService;
import com.sergiosantiago.conectados.services.UserService;

@RestController()
@RequestMapping("/notes")
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/notes")
    public HttpResponse<List<NoteDTO>> getNotes(@RequestParam(value = "id") Long id) {
        return noteService.getAllNotesByRoom(id);
    }

    @PostMapping("/delete")
    public HttpResponse<NoteDTO> deleteNote(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody NoteDTO noteDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return noteService.deleteNote(user, noteDTO);
    }

    @PostMapping("/create")
    public HttpResponse<NoteDTO> createNote(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody NoteDTO noteDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return noteService.createNote(user, noteDTO);
    }

}
