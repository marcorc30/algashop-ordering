package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
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

        return orderEntity;

    }

}
