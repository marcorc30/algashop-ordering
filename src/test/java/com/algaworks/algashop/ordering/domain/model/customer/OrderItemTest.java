package com.algaworks.algashop.ordering.domain.model.customer;

import com.algaworks.algashop.ordering.domain.model.order.OrderItem;
import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.product.ProductTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.product.Product;
import com.algaworks.algashop.ordering.domain.model.product.ProductName;
import com.algaworks.algashop.ordering.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.domain.model.order.OrderId;
import com.algaworks.algashop.ordering.domain.model.order.OrderItemId;
import com.algaworks.algashop.ordering.domain.model.product.ProductId;
import org.junit.jupiter.api.Test;

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