package com.sergiosantiago.conectados.dtos;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private Long userId;
    private Long roomId;
    private Set<Long> categoryIds;
    private Set<Long> shoppingItemIds;
    private String picture;
}
