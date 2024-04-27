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
public class ProductCategoryDTO {

    private Long id;
    private String name;
    private Set<Long> productIds;
    private Long userId;
    private Long roomId;
}
