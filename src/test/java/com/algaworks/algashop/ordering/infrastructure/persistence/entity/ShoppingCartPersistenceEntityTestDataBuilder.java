package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class ShoppingCartPersistenceEntityTestDataBuilder {

    public ShoppingCartPersistenceEntityTestDataBuilder() {
    }

    public static ShoppingCartPersistenceEntity existingShoppingCart(){
        return ShoppingCartPersistenceEntity.builder()
                .id(UUID.randomUUID())
                .customerId(UUID.randomUUID())
                .totalAmount(new BigDecimal("200.00"))
                .totalItens(10)
                .createdAt(OffsetDateTime.now())
                .build();
    }
}
