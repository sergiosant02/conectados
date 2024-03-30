package com.sergiosantiago.conectados.models.ext;

import java.util.HashSet;
import java.util.Set;

import com.sergiosantiago.conectados.dtos.RoomDTO;
import com.sergiosantiago.conectados.models.Product;
import com.sergiosantiago.conectados.models.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RoomExt {

    private Set<ProductCategory> productCategory;
    private Set<Product> product;
    private RoomDTO roomDTO;

    public RoomExt(){
        this.productCategory = new HashSet<>();
        this.product = new HashSet<>();
    }
}
