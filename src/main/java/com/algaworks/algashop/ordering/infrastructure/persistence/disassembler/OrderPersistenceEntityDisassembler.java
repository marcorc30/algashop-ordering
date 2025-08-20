package com.algaworks.algashop.ordering.infrastructure.persistence.disassembler;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderItem;
import com.algaworks.algashop.ordering.domain.model.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.entity.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


/*
Persistence -> Domain
 */
@Component
public class OrderPersistenceEntityDisassembler {

    public Order toDomainEntity(OrderPersistenceEntity persistenceEntity){

        return Order.existing()
                .id(new OrderId(persistenceEntity.getId()))
                .customerId(new CustomerId(persistenceEntity.getCustomerId()))
                .totalAmount(new Money(persistenceEntity.getTotalAmount()))
                .quantity(new Quantity(persistenceEntity.getTotalItems()))
                .status(OrderStatus.valueOf(persistenceEntity.getStatus()))
                .paymentMethod(PaymentMethod.valueOf(persistenceEntity.getPaymentMethod()))
                .placedAt(persistenceEntity.getPlacedAt())
                .paidAt(persistenceEntity.getPaidAt())
                .canceledAt(persistenceEntity.getCanceledAt())
                .readyAt(persistenceEntity.getReadyAt())
                .items(new HashSet<>())
                .version(persistenceEntity.getVersion())
                .billing(this.billing(persistenceEntity))
                .shipping(this.shipping(persistenceEntity))
                .items(toOrderItems(persistenceEntity))
                .build();

    }

    public Set<OrderItem> toOrderItems(OrderPersistenceEntity persistenceEntity){

        return persistenceEntity.getItems().stream()
                .map(ip -> {
                  Product product = Product.builder()
                          .id(new ProductId(ip.getProductId()))
                          .price(new Money(ip.getPrice()))
                          .name(new ProductName(ip.getProductName()))
                          .inStock(true)
                          .build();

                   return OrderItem.brandNew()
                           .orderId(new OrderId(ip.getOrderId()))
                           .product(product)
                           .quantity(new Quantity(ip.getQuantity()))
                           .build();

                }).collect(Collectors.toSet());

    }



    public Billing billing(OrderPersistenceEntity persistenceEntity){
        return Billing.builder()
                .phone(new Phone(persistenceEntity.getBilling().getPhone()))
                .fullName(new FullName(persistenceEntity.getBilling().getFirstName(), persistenceEntity.getBilling().getLastName()))
                .email(new Email(persistenceEntity.getBilling().getEmail()))
                .document(new Document(persistenceEntity.getBilling().getDocument()))
                .address(addressBilling(persistenceEntity))
                .build();
    }

    public Shipping shipping(OrderPersistenceEntity persistenceEntity){
        return Shipping.builder()
                .cost(new Money(persistenceEntity.getShipping().getCost()))
                .expectedDate(persistenceEntity.getShipping().getExpectedDate())
                .recipient(new Recipient(
                        new FullName(persistenceEntity.getShipping().getRecipient().getFirstName(), persistenceEntity.getShipping().getRecipient().getLastName()),
                        new Document(persistenceEntity.getShipping().getRecipient().getDocument()),
                        new Phone(persistenceEntity.getShipping().getRecipient().getPhone())
                ))
                .address(addressShipping(persistenceEntity))
                .build();
    }

    public Address addressBilling(OrderPersistenceEntity persistenceEntity){
        return Address.builder()
                .city(persistenceEntity.getBilling().getAddress().getCity())
                .state(persistenceEntity.getBilling().getAddress().getState())
                .complement(persistenceEntity.getBilling().getAddress().getComplement())
                .zipCode(new ZipCode(persistenceEntity.getBilling().getAddress().getZipCode()))
                .neighborhood(persistenceEntity.getBilling().getAddress().getNeighborhood())
                .street(persistenceEntity.getBilling().getAddress().getStreet())
                .number(persistenceEntity.getBilling().getAddress().getNumber())
                .build();
    }

    public Address addressShipping(OrderPersistenceEntity persistenceEntity){
        return Address.builder()
                .city(persistenceEntity.getShipping().getAddress().getCity())
                .state(persistenceEntity.getShipping().getAddress().getState())
                .complement(persistenceEntity.getShipping().getAddress().getComplement())
                .zipCode(new ZipCode(persistenceEntity.getShipping().getAddress().getZipCode()))
                .neighborhood(persistenceEntity.getShipping().getAddress().getNeighborhood())
                .street(persistenceEntity.getShipping().getAddress().getStreet())
                .number(persistenceEntity.getShipping().getAddress().getNumber())
                .build();
    }

}
