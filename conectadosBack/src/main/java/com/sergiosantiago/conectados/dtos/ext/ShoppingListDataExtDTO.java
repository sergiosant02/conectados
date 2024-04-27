package com.sergiosantiago.conectados.dtos.ext;

import java.util.HashSet;
import java.util.Set;

import com.sergiosantiago.conectados.dtos.ShoppingItemDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShoppingListDataExtDTO {
    private Long id;
    private String name;
    private Boolean completed;
    private Long roomId;
    private Set<ShoppingItemDTO> items;

    public ShoppingListDataExtDTO() {
        this.items = new HashSet<>();
    }

}
