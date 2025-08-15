package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.utility.IdGenerator;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.BillingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.RecipientEmbedabble;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.ShippingEmbeddable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class OrderPersistenceEntityTestDataBuilder {

    public OrderPersistenceEntityTestDataBuilder() {
    }

    public static OrderPersistenceEntity.OrderPersistenceEntityBuilder existingOrder(){
        return OrderPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .customerId(IdGenerator.generateTimeBaseUUID())
                .totalItems(2)
                .totalAmount(new BigDecimal(1000))
                .status("DRAFT")
                .paymentMethod("CREDIT_CARD")
                .billing(billing())
                .shipping(shipping())
                .placedAt(OffsetDateTime.now());
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
