package com.sergiosantiago.conectados.dtos.ext;

import java.util.HashSet;
import java.util.Set;

import com.sergiosantiago.conectados.dtos.ProductCategoryDTO;
import com.sergiosantiago.conectados.dtos.ProductDTO;
import com.sergiosantiago.conectados.dtos.RoomDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RoomExtDTO {

    private Set<ProductCategoryDTO> productCategoryDTOs;
    private Set<ProductDTO> productDTOs;
    private RoomDTO roomDTO;

    public RoomExtDTO(){
        this.productCategoryDTOs = new HashSet<>();
        this.productDTOs = new HashSet<>();
    }
}
