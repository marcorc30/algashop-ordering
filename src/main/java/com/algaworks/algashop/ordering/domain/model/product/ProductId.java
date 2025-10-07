package com.algaworks.algashop.ordering.domain.model.product;

import com.algaworks.algashop.ordering.domain.model.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record ProductId(UUID value) {

    public ProductId(UUID value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public ProductId(){
        this(IdGenerator.generateTimeBaseUUID());
    }


}
