package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import com.algaworks.algashop.ordering.domain.model.IdGenerator;
import com.algaworks.algashop.ordering.infrastructure.persistence.commons.AddressEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.BillingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.RecipientEmbedabble;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.ShippingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.OrderItemPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.OrderPersistenceEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;

public class OrderPersistenceEntityTestDataBuilder {

    public OrderPersistenceEntityTestDataBuilder() {
    }

    public static OrderPersistenceEntity.OrderPersistenceEntityBuilder existingOrder(){
        return OrderPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .customer(CustomerPersistenceEntityTestDataBuilder.aCustomer().build())
                .totalItems(2)
                .totalAmount(new BigDecimal(1000))
                .status("DRAFT")
                .paymentMethod("CREDIT_CARD")
                .billing(billing())
                .shipping(shipping())
                .placedAt(OffsetDateTime.now())
                .items(Set.of(
                        existingItem().build(),
                        existingItemAlt().build()

                ));
    }

    public static OrderItemPersistenceEntity.OrderItemPersistenceEntityBuilder existingItem(){
        return OrderItemPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .price(new BigDecimal(500))
                .quantity(2)
                .totalAmount(new BigDecimal(1000))
                .productName("Notebook")
                .productId(IdGenerator.generateTimeBaseUUID());
    }

    public static OrderItemPersistenceEntity.OrderItemPersistenceEntityBuilder existingItemAlt(){
        return OrderItemPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .price(new BigDecimal(250))
                .quantity(1)
                .totalAmount(new BigDecimal(250))
                .productName("Mouse Pad")
                .productId(IdGenerator.generateTimeBaseUUID());
    }

    public static BillingEmbeddable billing(){
        return BillingEmbeddable.builder()
                .email("marcorc30@gmail.com")
                .phone("5555555")
                .lastName("marco")
                .firstName("cavalheiro")
                .document("55566666")
                .address(addressBilling())
                .build();
    }

    public static ShippingEmbeddable shipping(){
        return ShippingEmbeddable.builder()
                .address(addressShipping())
                .expectedDate(LocalDate.now())
                .recipient(new RecipientEmbedabble(
                        "marco",
                        "rodrigues",
                        "555444",
                        "6155455"
                ))
                .cost(new BigDecimal("100"))
                .build();
    }

    public static AddressEmbeddable addressBilling(){
        return AddressEmbeddable.builder()
                .city("guara")
                .state("df")
                .complement("conj u")
                .zipCode("55555")
                .neighborhood("teste")
                .street("teste")
                .number("55555")
                .build();
    }

    public static AddressEmbeddable addressShipping(){
        return AddressEmbeddable.builder()
                .city("guara")
                .state("df")
                .complement("conj u")
                .zipCode("55555")
                .neighborhood("teste")
                .street("teste")
                .number("55555")
                .build();
    }

}
