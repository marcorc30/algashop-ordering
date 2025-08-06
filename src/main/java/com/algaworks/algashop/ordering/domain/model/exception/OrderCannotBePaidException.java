package com.algaworks.algashop.ordering.domain.model.exception;

import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;

public class OrderCannotBePaidException extends DomainException {
    public OrderCannotBePaidException(OrderId id) {
        super(String.format(ErrorMessage.ERROR_ODER_CANNOT_BE_PAID, id));
    }
}
