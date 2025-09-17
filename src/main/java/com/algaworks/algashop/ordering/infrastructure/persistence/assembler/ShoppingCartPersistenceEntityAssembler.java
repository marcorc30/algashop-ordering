package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCartItem;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartItemPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ShoppingCartPersistenceEntityAssembler {

    public ShoppingCartPersistenceEntity fromDomain(ShoppingCart domain){
        return merge(new ShoppingCartPersistenceEntity(), domain);
    }

    public ShoppingCartPersistenceEntity merge(ShoppingCartPersistenceEntity entity, ShoppingCart domain){

        entity.setId(domain.id().value());
        entity.setVersion(domain.version());
        entity.setTotalAmount(domain.totalAmount().value());
        entity.setCustomerId(domain.customerId().value());
        entity.setCreateAt(domain.createdAt());
        entity.setTotalItens(domain.totalItens().value());
        entity.setItems(converterLista(domain.items()));

        return entity;
    }


    private Set<ShoppingCartItemPersistenceEntity> converterLista(Set<ShoppingCartItem> itemsDomain){

        return itemsDomain.stream()
                .map(id -> mergeItems(new ShoppingCartItemPersistenceEntity(), id))
                .collect(Collectors.toSet());

    }


    public ShoppingCartItemPersistenceEntity fromDomainItems(ShoppingCartItem domain){
        return mergeItems(new ShoppingCartItemPersistenceEntity(), domain);
    }

    public ShoppingCartItemPersistenceEntity mergeItems(ShoppingCartItemPersistenceEntity entity, ShoppingCartItem domain){
        entity.setId(domain.id().value());
        entity.setPrice(domain.price().value());
        entity.setShoppingCartId(domain.shoppingCartId().value());
        entity.setAvailable(domain.available());
        entity.setProductId(domain.productId().value());
        entity.setProductName(domain.productName().value());
        entity.setTotalAmount(domain.totalAmount().value());
        entity.setQuantity(domain.quantity().value());

        return entity;
    }


}
