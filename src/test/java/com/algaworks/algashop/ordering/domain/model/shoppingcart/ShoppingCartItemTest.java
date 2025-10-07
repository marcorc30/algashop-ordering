package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.product.ProductName;
import com.algaworks.algashop.ordering.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.domain.model.product.ProductId;
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