package com.algaworks.algashop.ordering.domain.entity.factory;


import com.algaworks.algashop.ordering.domain.entity.Order;
import com.algaworks.algashop.ordering.domain.entity.PaymentMethod;
import com.algaworks.algashop.ordering.domain.valueobject.*;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class OrderFactoryTest {

    @Test
    void criarPedidoRascunhoNovo(){

        Address address = Address.builder()
                .complement("teste")
                .state("teste")
                .city("teste")
                .zipCode(new ZipCode("55555"))
                .number("5555")
                .street("teste")
                .neighborhood("teste")
                .build();

        Billing billing = Billing.builder()
                .email(new Email("marco@email.com"))
                .address(address)
                .phone(new Phone("555-555-555"))
                .document(new Document("4455414"))
                .fullName(new FullName("teste", "teste"))
                .build();

        Recipient recipient = Recipient.builder()
                .phone(new Phone("555-555-555"))
                .fullName(new FullName("teste", "teste"))
                .document(new Document("55555"))
                .build();


        Shipping shipping = Shipping.builder()
                .expectedDate(LocalDate.now())
                .cost(new Money("10.0"))
                .address(address)
                .recipient(recipient)
                .build();

        Product product = Product.builder()
                .id(new ProductId())
                .inStock(true)
                .name(new ProductName("teste"))
                .price(new Money("10.0"))
                .build();



        Quantity quantity = Quantity.ZER0;

        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;

        Order order = OrderFactory.filled(new CustomerId(), shipping, billing, paymentMethod, product, quantity);
        order.addItem(product, quantity);


        Assertions.assertWith(order,
                o -> Assertions.assertThat(o.shipping()).isEqualTo(shipping),
                o -> Assertions.assertThat(o.billing()).isEqualTo(billing),
                o -> Assertions.assertThat(o.items()).isNotEmpty()
        );

    }

}