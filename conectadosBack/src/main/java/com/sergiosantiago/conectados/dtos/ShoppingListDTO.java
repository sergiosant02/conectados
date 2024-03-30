package com.sergiosantiago.conectados.dtos;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingListDTO {

    private Long id;
    private String name;
    private Boolean completed;
    private Long roomId;
    private Set<Long> itemIds;
}