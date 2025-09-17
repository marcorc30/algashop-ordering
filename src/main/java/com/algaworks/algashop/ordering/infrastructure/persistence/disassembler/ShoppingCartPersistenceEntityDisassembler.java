package com.algaworks.algashop.ordering.infrastructure.persistence.disassembler;

import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCartItem;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.model.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartItemPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ShoppingCartPersistenceEntityDisassembler {

    public ShoppingCart fromEntity(ShoppingCartPersistenceEntity entity, ShoppingCart domain){

        return ShoppingCart.existing()
                .id(new ShoppingCartId(entity.getId()))
                .createdAt(entity.getCreatedAt())
                .totalAmount(new Money(entity.getTotalAmount()))
                .totalItens(new Quantity(entity.getTotalItens()))
                .customerId(new CustomerId(entity.getCustomerId()))
                .items(converteItems(entity.getItems()))
                .build();

    }


    private Set<ShoppingCartItem> converteItems(Set<ShoppingCartItemPersistenceEntity> entitySet){

        return entitySet
                .stream()
                .map(es -> fromEntityItem(es, new ShoppingCartItem()))
                .collect(Collectors.toSet());


    }

    private ShoppingCartItem fromEntityItem(ShoppingCartItemPersistenceEntity entity, ShoppingCartItem domain){

        return ShoppingCartItem.brandNew()
                .shoppingCartId(new ShoppingCartId(entity.getShoppingCartId()))
                .price(new Money(entity.getPrice()))
                .productName(new ProductName(entity.getProductName()))
                .productId(new ProductId(entity.getProductId()))
                .available(entity.getAvailable())
                .quantity(new Quantity(entity.getQuantity()))
                .build();

    }

}
