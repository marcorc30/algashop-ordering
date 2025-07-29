package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.utility.IdGenerator;
import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Product;
import com.algaworks.algashop.ordering.domain.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderItemId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    void testOrderItem(){
        OrderItem.existing()
                .id(new OrderItemId())
                .orderId(new OrderId())
                .price(new Money("10.0"))
                .quantity(new Quantity(2))
                .productId(new ProductId())
                .productName(new ProductName("produto a"))
                .totalAmount(new Money("20.0"))
                .build();
    }

    @Test
    void newOrderId(){
        Product product = ProductTestDataBuilder.aProduct().build();
        OrderItem.brandNew()
                .orderId(new OrderId())
                .product(product)
                .quantity(new Quantity(10))
                .build();
    }

}