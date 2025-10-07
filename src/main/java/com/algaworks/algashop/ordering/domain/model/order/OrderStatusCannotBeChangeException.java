package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.DomainException;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessage.ERROR_ORDER_STATUS_CANNOT_BE_CHANGED;

public class OrderStatusCannotBeChangeException extends DomainException {

    public OrderStatusCannotBeChangeException(String message) {
        super(message);
    }

    public OrderStatusCannotBeChangeException(OrderId id, OrderStatus status, OrderStatus newStatus){
        super(String.format(ERROR_ORDER_STATUS_CANNOT_BE_CHANGED, id, status, newStatus));
    }



}
