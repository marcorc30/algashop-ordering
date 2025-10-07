package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.DomainException;
import com.algaworks.algashop.ordering.domain.model.ErrorMessage;

public class OrderCannotBePaidException extends DomainException {
    public OrderCannotBePaidException(OrderId id) {
        super(String.format(ErrorMessage.ERROR_ODER_CANNOT_BE_PAID, id));
    }
}
