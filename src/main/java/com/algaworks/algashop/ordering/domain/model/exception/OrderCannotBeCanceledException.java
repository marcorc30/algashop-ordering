package com.algaworks.algashop.ordering.domain.model.exception;

import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;

public class OrderCannotBeCanceledException extends DomainException {
    public OrderCannotBeCanceledException(OrderId id) {
        super(String.format(ErrorMessage.ERROR_ORDER_CANNOT_BE_CANCELED, id));
    }
}
