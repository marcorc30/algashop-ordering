package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.model.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ShoppingCartItemId;

import java.util.UUID;

public class ShoppingCartTestDataBuilder {

    public static ShoppingCart newShoppingCart(){

        ShoppingCart shoppingCart = ShoppingCart.startShopping(new CustomerId());

        Product product = Product.builder()
                .id(new ProductId())
                .price(new Money("10.00"))
                .name(new ProductName("Produto teste"))
                .inStock(true)
                .build();


        ShoppingCartItem shoppingCartItem = ShoppingCartItem.brandNew()
                .shoppingCartId(shoppingCart.id())
                .price(product.price())
                .quantity(new Quantity(5))
                .available(true)
                .productId(product.id())
                .productName(product.name())
                .id(new ShoppingCartItemId())
                .build();


        shoppingCart.addItem(product, new Quantity(10));

        return shoppingCart;



    }

}
