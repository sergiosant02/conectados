package com.sergiosantiago.conectados.models.ext;

import java.util.HashSet;
import java.util.Set;

import com.sergiosantiago.conectados.models.Product;
import com.sergiosantiago.conectados.models.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProductExt {

    private Set<Product> products;
    private Set<ProductCategory> categories;

    public ProductExt() {
        this.categories = new HashSet<>();
        this.products = new HashSet<>();
    }

}
