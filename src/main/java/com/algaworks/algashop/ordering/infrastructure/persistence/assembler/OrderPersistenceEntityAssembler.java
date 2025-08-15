package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.BillingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.RecipientEmbedabble;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.ShippingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderPersistenceEntityAssembler {

    public OrderPersistenceEntity fromDomain(Order order){
        return merge(new OrderPersistenceEntity(), order);
    }

    public OrderPersistenceEntity merge(OrderPersistenceEntity orderEntity, Order orderDomain){

        orderEntity.setId(orderDomain.id().value().toLong());
        orderEntity.setCustomerId(orderDomain.customerId().value());
        orderEntity.setTotalAmount(orderDomain.totalAmount().value());
        orderEntity.setTotalItems(orderDomain.quantity().value());
        orderEntity.setStatus(orderDomain.status().name());
        orderEntity.setPaymentMethod(orderDomain.paymentMethod().name());
        orderEntity.setPlacedAt(orderDomain.placedAt());
        orderEntity.setPaidAt(orderDomain.paidAt());
        orderEntity.setCanceledAt(orderDomain.canceledAt());
        orderEntity.setReadyAt(orderDomain.readyAt());
        orderEntity.setVersion(orderDomain.version());
        orderEntity.setBilling(this.billing(orderDomain));
        orderEntity.setShipping(this.shipping(orderDomain));

        return orderEntity;

    }

    public BillingEmbeddable billing(Order orderDomain){
        return BillingEmbeddable.builder()
                .email(orderDomain.billing().email().value())
                .phone(orderDomain.billing().phone().value())
                .lastName(orderDomain.billing().fullName().lastName())
                .firstName(orderDomain.billing().fullName().firstName())
                .document(orderDomain.billing().document().value())
                .address(addressBilling(orderDomain))
                .build();
    }

    public ShippingEmbeddable shipping(Order orderDomain){
        return ShippingEmbeddable.builder()
                .address(addressShipping(orderDomain))
                .expectedDate(orderDomain.shippingInfo().expectedDate())
                .recipient(new RecipientEmbedabble(
                        orderDomain.shipping().recipient().fullName().firstName(),
                        orderDomain.shipping().recipient().fullName().lastName(),
                        orderDomain.shipping().recipient().document().value(),
                        orderDomain.shipping().recipient().phone().value()
                ))
                .cost(orderDomain.shipping().cost().value())
                .build();
    }

    public AddressEmbeddable addressBilling(Order orderDomain){
        return AddressEmbeddable.builder()
                .city(orderDomain.billing().address().city())
                .state(orderDomain.billing().address().state())
                .complement(orderDomain.billing().address().complement())
                .zipCode(orderDomain.billing().address().zipCode().value())
                .neighborhood(orderDomain.billing().address().neighborhood())
                .street(orderDomain.billing().address().street())
                .number(orderDomain.billing().address().number())
                .build();
    }

    public AddressEmbeddable addressShipping(Order orderDomain){
        return AddressEmbeddable.builder()
                .city(orderDomain.shipping().address().city())
                .state(orderDomain.shipping().address().state())
                .complement(orderDomain.shipping().address().complement())
                .zipCode(orderDomain.shipping().address().zipCode().value())
                .neighborhood(orderDomain.shipping().address().neighborhood())
                .street(orderDomain.shipping().address().street())
                .number(orderDomain.shipping().address().number())
                .build();
    }

}
