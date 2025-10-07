package com.algaworks.algashop.ordering.domain.model.customer;

import com.algaworks.algashop.ordering.domain.model.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID value) {

    public CustomerId(UUID value) {
        Objects.requireNonNull(value);
        this.value = value;
    }


    public Long toLong(){
        return null;
    }

    public CustomerId(){
        this(IdGenerator.generateTimeBaseUUID());
    }


    @Override
    public String toString() {
        return value.toString();
    }

}
