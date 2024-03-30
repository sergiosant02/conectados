package com.sergiosantiago.conectados.dtos.ext;

import java.util.HashSet;
import java.util.Set;

import com.sergiosantiago.conectados.dtos.ShoppingItemDTO;
import com.sergiosantiago.conectados.dtos.ShoppingListDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ShoppingListExtDTO {

    private ShoppingListDTO shoppingListDTO;
    private Set<ShoppingItemDTO> shoppingItemDTOs;

    public ShoppingListExtDTO() {
        this.shoppingItemDTOs = new HashSet<>();
    }

}
