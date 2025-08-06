package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.model.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ShoppingCartItemId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ShoppingCartItemTest {

    @Test
    void dado_um_item_novo_quando_cadastrar_entao_calcular_total(){
        ShoppingCartItem shoppingCartItem = ShoppingCartItem.brandNew()
                .id(new ShoppingCartItemId())
                .shoppingCartId(new ShoppingCartId())
                .productId(new ProductId())
                .quantity(new Quantity(10))
                .productName(new ProductName("teste"))
                .price(new Money("10.0"))
                .available(true)
                .build();

        Assertions.assertThat(shoppingCartItem.price()).isEqualTo(new Money("10.0"));
        Assertions.assertThat(shoppingCartItem.productName()).isEqualTo(new ProductName("teste"));
        Assertions.assertThat(shoppingCartItem.totalAmount()).isEqualTo(new Money("100.0"));


    }



}