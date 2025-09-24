package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import com.algaworks.algashop.ordering.domain.model.utility.IdGenerator;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity.ShoppingCartPersistenceEntityBuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public class ShoppingCartPersistenceEntityTestDataBuilder {
    private ShoppingCartPersistenceEntityTestDataBuilder() {
    }

    public static ShoppingCartPersistenceEntityBuilder existingShoppingCart() {
        return ShoppingCartPersistenceEntity.builder()
                .id(IdGenerator.generateTimeBaseUUID())
                .customer(CustomerPersistenceEntityTestDataBuilder.aCustomer().build())
                .totalItems(3)
                .totalAmount(new BigDecimal(1250))
                .createdAt(OffsetDateTime.now())
                .items(Set.of(
                        existingItem().build(),
                        existingItemAlt().build()
                ));
    }

    public static ShoppingCartItemPersistenceEntity.ShoppingCartItemPersistenceEntityBuilder existingItem() {
        return ShoppingCartItemPersistenceEntity.builder()
                .id(IdGenerator.generateTimeBaseUUID())
                .price(new BigDecimal(500))
                .quantity(2)
                .totalAmount(new BigDecimal(1000))
                .name("Notebook")
                .productId(IdGenerator.generateTimeBaseUUID());
    }

    public static ShoppingCartItemPersistenceEntity.ShoppingCartItemPersistenceEntityBuilder existingItemAlt() {
        return ShoppingCartItemPersistenceEntity.builder()
                .id(IdGenerator.generateTimeBaseUUID())
                .price(new BigDecimal(250))
                .quantity(1)
                .totalAmount(new BigDecimal(250))
                .name("Mouse pad")
                .productId(IdGenerator.generateTimeBaseUUID());
    }
}
