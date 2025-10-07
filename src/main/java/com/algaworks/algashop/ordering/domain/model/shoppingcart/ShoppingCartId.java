package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record ShoppingCartId(UUID value) {

    public ShoppingCartId(UUID value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public ShoppingCartId(){
        this(IdGenerator.generateTimeBaseUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
