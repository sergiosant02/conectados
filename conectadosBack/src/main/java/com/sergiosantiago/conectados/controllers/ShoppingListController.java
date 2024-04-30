package com.sergiosantiago.conectados.controllers;

import java.util.Set;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sergiosantiago.conectados.Utils.Utils;
import com.sergiosantiago.conectados.dtos.ProductDTO;
import com.sergiosantiago.conectados.dtos.ShoppingItemDTO;
import com.sergiosantiago.conectados.dtos.ShoppingListDTO;
import com.sergiosantiago.conectados.dtos.ext.ShoppingListDataExtDTO;
import com.sergiosantiago.conectados.dtos.ext.ShoppingListExtDTO;
import com.sergiosantiago.conectados.models.User;
import com.sergiosantiago.conectados.models.auth.HttpResponse;
import com.sergiosantiago.conectados.services.ShoppingListService;
import com.sergiosantiago.conectados.services.UserService;

@RestController()
@RequestMapping("/shoppingList")
public class ShoppingListController {

    private ShoppingListService shoppingListService;
    private UserService userService;

    public ShoppingListController(ShoppingListService shoppingListService, UserService userService) {
        this.shoppingListService = shoppingListService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public HttpResponse<Set<ShoppingListDataExtDTO>> getAllShoppingListsOfRoom(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(value = "id") Long id) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return this.shoppingListService.getAllShoppingList(user, id);
    }

    @PostMapping("/create")
    public HttpResponse<ShoppingListDTO> createShoppingList(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ShoppingListDTO shoppingListDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return this.shoppingListService.createShoppingList(user, shoppingListDTO);
    }

    @PostMapping("/getInfoAbout")
    public HttpResponse<ShoppingListExtDTO> getAllInfoAboutShoppingList(
            @AuthenticationPrincipal UserDetails userDetails, @RequestBody ShoppingListDTO shoppingListDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return this.shoppingListService.getAllInfoAboutShoppingList(user, shoppingListDTO);
    }

    @PostMapping("/delete")
    public HttpResponse<ShoppingListDataExtDTO> deleteShoppingList(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ShoppingListDataExtDTO shoppingListDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return this.shoppingListService.deleteShoppingList(user, shoppingListDTO);
    }

    @PostMapping("/deleteProduct")
    public HttpResponse<ProductDTO> deleteProduct(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ProductDTO productDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return this.shoppingListService.deleteProduct(user, productDTO);
    }

    @PostMapping("/changeStatus")
    public HttpResponse<ShoppingListDataExtDTO> changeCompleteStatus(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ShoppingListDataExtDTO shoppingListDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return this.shoppingListService.changeCompleteStatus(user, shoppingListDTO);
    }

    @PostMapping("/items/create")
    public HttpResponse<ShoppingItemDTO> createShoppingItem(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ShoppingItemDTO shoppingItemDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return this.shoppingListService.createShoppingItem(user, shoppingItemDTO);
    }

    @PostMapping("/items/delete")
    public HttpResponse<ShoppingItemDTO> deleteShoppingItem(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ShoppingItemDTO shoppingItemDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return this.shoppingListService.deleteShoppingItem(user, shoppingItemDTO);
    }

    @PostMapping("/items/update")
    public HttpResponse<ShoppingItemDTO> updateQuantityAndPriceShoppingItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ShoppingItemDTO shoppingItemDTO) {
        User user = Utils.getUserFromRequest(userDetails, userService);
        return this.shoppingListService.updateQuantityAndPriceShoppingItem(user, shoppingItemDTO);
    }

}
