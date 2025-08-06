package com.algaworks.algashop.ordering.domain.model.valueobject.id;

import com.algaworks.algashop.ordering.domain.model.utility.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID value) {

    public CustomerId(UUID value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public CustomerId(){
        this(IdGenerator.generateTimeBaseUUID());
    }


    @Override
    public String toString() {
        return value.toString();
    }
}
