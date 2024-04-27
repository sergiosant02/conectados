package com.sergiosantiago.conectados.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingItemDTO {

    private Long id;
    private Double quantity;
    private Long shoppingListId;
    private Long productId;
    private Double value;
    private String name;
}
