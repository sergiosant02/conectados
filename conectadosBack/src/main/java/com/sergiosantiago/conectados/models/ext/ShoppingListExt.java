package com.sergiosantiago.conectados.models.ext;

import java.util.HashSet;
import java.util.Set;

import com.sergiosantiago.conectados.models.ShoppingItem;
import com.sergiosantiago.conectados.models.ShoppingList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ShoppingListExt {

    private ShoppingList shoppingList;
    private Set<ShoppingItem> shoppingItems;

    public ShoppingListExt() {
        this.shoppingItems = new HashSet<>();
    }
}
