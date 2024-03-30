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
public class RoomDTO {

    private Long id;
    private String name;
    private String code;
    private String description;
    private Long ownerId;
    private Set<Long> belongToUserIds;
    private Set<Long> productIds;
    private Set<Long> noteIds;
    private Set<Long> shoppingListIds;
    private Set<Long> productCategoryIds;
}
