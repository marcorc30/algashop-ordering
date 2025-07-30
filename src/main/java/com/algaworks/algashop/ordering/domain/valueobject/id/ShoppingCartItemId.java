package com.algaworks.algashop.ordering.domain.valueobject.id;

import com.algaworks.algashop.ordering.domain.utility.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record ShoppingCartItemId(UUID value) {

    public ShoppingCartItemId(UUID value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public ShoppingCartItemId(){
        this(IdGenerator.generateTimeBaseUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
