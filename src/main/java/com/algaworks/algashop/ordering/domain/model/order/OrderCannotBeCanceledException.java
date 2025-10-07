package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.DomainException;
import com.algaworks.algashop.ordering.domain.model.ErrorMessage;

public class OrderCannotBeCanceledException extends DomainException {
    public OrderCannotBeCanceledException(OrderId id) {
        super(String.format(ErrorMessage.ERROR_ORDER_CANNOT_BE_CANCELED, id));
    }
}
