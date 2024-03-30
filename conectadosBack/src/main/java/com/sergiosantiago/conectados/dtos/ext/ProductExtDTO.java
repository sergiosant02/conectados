package com.sergiosantiago.conectados.dtos.ext;

import java.util.HashSet;
import java.util.Set;

import com.sergiosantiago.conectados.dtos.ProductCategoryDTO;
import com.sergiosantiago.conectados.dtos.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductExtDTO {
    private Set<ProductDTO> products;
    private Set<ProductCategoryDTO> categories;

    public ProductExtDTO() {
        this.categories = new HashSet<>();
        this.products = new HashSet<>();
    }

}