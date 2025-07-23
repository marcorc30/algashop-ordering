package com.algaworks.algashop.ordering.domain.exception;

import com.algaworks.algashop.ordering.domain.entity.Order;
import com.algaworks.algashop.ordering.domain.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessage.ERROR_ORDER_STATUS_CANNOT_BE_CHANGED;

public class OrderStatusCannotBeChangeException extends DomainException {

    public OrderStatusCannotBeChangeException(String message) {
        super(message);
    }

    public OrderStatusCannotBeChangeException(OrderId id, OrderStatus status, OrderStatus newStatus){
        super(String.format(ERROR_ORDER_STATUS_CANNOT_BE_CHANGED, id, status, newStatus));
    }



}
