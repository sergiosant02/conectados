package com.sergiosantiago.conectados.controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergiosantiago.conectados.Utils.Utils;
import com.sergiosantiago.conectados.dtos.RoomDTO;
import com.sergiosantiago.conectados.models.User;
import com.sergiosantiago.conectados.services.UserService;

@RestController()
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/roomsOwner")
    public List<RoomDTO> getRoomsOfUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return userService.getListRoomsOwner(user);
    }

    @GetMapping("/roomsGuest")
    public List<RoomDTO> getRoomsGuest(@AuthenticationPrincipal UserDetails userDetails) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return userService.getListRoomsGuest(user);
    }

    @GetMapping("/roomsMember")
    public List<RoomDTO> getRoomsMember(@AuthenticationPrincipal UserDetails userDetails) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return userService.getRoomsMember(user);
    }

}
