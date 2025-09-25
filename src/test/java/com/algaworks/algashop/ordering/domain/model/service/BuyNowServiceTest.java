package com.algaworks.algashop.ordering.domain.model.service;

import com.algaworks.algashop.ordering.domain.model.entity.*;
import com.algaworks.algashop.ordering.domain.model.exception.ProductOutOfStockException;
import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;
import static org.junit.jupiter.api.Assertions.*;

class BuyNowServiceTest {

    private BuyNowService service = new BuyNowService();

    @Test
    public void validarCompraImediataDeProdutoComEstoque(){


        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

        Product product = ProductTestDataBuilder.aProductAltRamMemory().build();

        Address address = Address.builder()
                .city("city")
                .state("state")
                .zipCode(new ZipCode("55555"))
                .complement("55555")
                .neighborhood("neighborhood")
                .number("66554")
                .complement("complement")
                .street("street")
                .build();

        Order order = Order.draft(DEFAULT_CUSTOMER_ID);
        Shipping shipping = Shipping.builder()
                .cost(new Money("20"))
                .expectedDate(LocalDate.now())
                .recipient(Recipient.builder()
                        .phone(new Phone("555-555-555"))
                        .document(new Document("55444"))
                        .fullName(new FullName("fulano", "de tal"))
                        .build())
                .address(address)
                .build();

        Billing billing = Billing.builder()
                .phone(new Phone("555-555-555"))
                .document(new Document("55444"))
                .email(new Email("fulano@gmail.com"))
                .address(address)
                .fullName(new FullName("filano", "de tal"))
                .build();

        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;

        Quantity quantity = new Quantity(1);

        order.addItem(product, quantity);

        Order buyNow = service.buyNow(product, customer.id(), billing, shipping, quantity, paymentMethod);

        Assertions.assertThat(buyNow.status()).isEqualTo(OrderStatus.PLACED);


    }


    @Test
    public void validarCompraImediataDeProdutoSemEstoque(){


        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

        Product product = ProductTestDataBuilder.aProductUnavailable().build();

        Address address = Address.builder()
                .city("city")
                .state("state")
                .zipCode(new ZipCode("55555"))
                .complement("55555")
                .neighborhood("neighborhood")
                .number("66554")
                .complement("complement")
                .street("street")
                .build();

        Order order = Order.draft(DEFAULT_CUSTOMER_ID);
        Shipping shipping = Shipping.builder()
                .cost(new Money("20"))
                .expectedDate(LocalDate.now())
                .recipient(Recipient.builder()
                        .phone(new Phone("555-555-555"))
                        .document(new Document("55444"))
                        .fullName(new FullName("fulano", "de tal"))
                        .build())
                .address(address)
                .build();

        Billing billing = Billing.builder()
                .phone(new Phone("555-555-555"))
                .document(new Document("55444"))
                .email(new Email("fulano@gmail.com"))
                .address(address)
                .fullName(new FullName("filano", "de tal"))
                .build();

        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;

        Quantity quantity = new Quantity(1);

        Assertions.assertThatExceptionOfType(ProductOutOfStockException.class)
                        .isThrownBy(() -> order.addItem(product, quantity));



    }

}