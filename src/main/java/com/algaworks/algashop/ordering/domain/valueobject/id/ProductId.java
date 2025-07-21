package com.algaworks.algashop.ordering.domain.valueobject.id;

import com.algaworks.algashop.ordering.domain.utility.IdGenerator;

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
