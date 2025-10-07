package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.IdGenerator;
import io.hypersistence.tsid.TSID;

import java.util.Objects;

public record OrderItemId(TSID value) {

    public OrderItemId {
        Objects.requireNonNull(value);
    }

    public OrderItemId(Long value){
        this(TSID.from(value));
    }

    public OrderItemId(String value){
        this(TSID.from(value));
    }

    public OrderItemId(){
        this(IdGenerator.generateTSID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
