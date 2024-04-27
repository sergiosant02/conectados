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
import com.sergiosantiago.conectados.dtos.ProductCategoryDTO;
import com.sergiosantiago.conectados.dtos.RoomDTO;
import com.sergiosantiago.conectados.dtos.UserDTO;
import com.sergiosantiago.conectados.dtos.ext.ProductExtDTO;
import com.sergiosantiago.conectados.dtos.ext.RoomExtDTO;
import com.sergiosantiago.conectados.models.User;
import com.sergiosantiago.conectados.models.auth.HttpResponse;
import com.sergiosantiago.conectados.services.RoomService;
import com.sergiosantiago.conectados.services.UserService;

@RestController()
@RequestMapping("/rooms")
public class RoomController {

    private RoomService roomService;
    private UserService userService;

    public RoomController(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public RoomDTO createRoom(@AuthenticationPrincipal UserDetails userDetails, @RequestBody RoomDTO roomDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        RoomDTO room = roomService.createRoom(roomDTO, user);

        return room;
    }

    @GetMapping("/listMembers")
    public List<UserDTO> getMembersOfRoom(@RequestParam(value = "id") Long id) {
        return roomService.getUserMembersInRoom(id);
    }

    @PostMapping("/join")
    public HttpResponse<RoomDTO> joinToRoom(@RequestBody RoomDTO roomDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        String code = roomDTO.getCode();
        User user = Utils.getUserFromRequest(userDetails, userService);
        return roomService.joinToRoom(code, user);
    }

    @PostMapping("/leave")
    public HttpResponse<RoomDTO> leaveRoom(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RoomDTO roomDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return roomService.leaveRoom(roomDTO.getId(), user);
    }

    @PostMapping("/fire")
    public HttpResponse<RoomDTO> fireMember(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RoomDTO roomDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);

        return roomService.fireMember(roomDTO, user);
    }

    @PostMapping("/delete")
    public HttpResponse<RoomDTO> deleteRoom(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RoomDTO roomDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);

        return roomService.deleteRoom(user, roomDTO.getId());
    }

    @GetMapping("/listProducts")
    public HttpResponse<ProductExtDTO> getMethodName(@RequestParam(value = "id") Long id) {
        return roomService.getProductDTOsOfRoom(id);
    }

    @GetMapping("/allRoomInfo")
    public HttpResponse<RoomExtDTO> getAllRoomInfo(
            @RequestParam(value = "id") Long id) {
        return roomService.getAllRoomInfo(id);
    }

    @PostMapping("/deleteCategory")
    public HttpResponse<ProductCategoryDTO> deleteCategoryForRoom(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ProductCategoryDTO productCategoryDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return roomService.deleteCategory(user, productCategoryDTO.getId());
    }

    @PostMapping("/createCatgeory")
    public HttpResponse<ProductCategoryDTO> createCategoryForRoom(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ProductCategoryDTO productCategoryDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return roomService.createProductCategory(user, productCategoryDTO);
    }

}
