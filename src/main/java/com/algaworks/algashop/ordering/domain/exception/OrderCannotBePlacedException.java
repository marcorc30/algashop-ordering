package com.algaworks.algashop.ordering.domain.exception;

import com.algaworks.algashop.ordering.domain.entity.Order;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;

import java.io.OptionalDataException;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessage.*;

public class OrderCannotBePlacedException extends DomainException {

    public OrderCannotBePlacedException(String message){
        super(message);
    }

    public static OrderCannotBePlacedException noItems(OrderId id ){
        return new OrderCannotBePlacedException(String.format(ERROR_ODER_CANNOT_BE_PLACED_HAS_NOT_ITEMS, id));
    }

    public static OrderCannotBePlacedException noShippingInfo(OrderId id){
        return new OrderCannotBePlacedException(String.format(ERROR_ODER_CANNOT_BE_PLACED_HAS_NO_SHIPPING_INFO, id));
    }

    public static OrderCannotBePlacedException noBillingInfo(OrderId id){
        return new OrderCannotBePlacedException(String.format(ERROR_ODER_CANNOT_BE_PLACED_HAS_BILLING_INFO, id));
    }


    public static OrderCannotBePlacedException noPaymentMethod(OrderId id){
        return new OrderCannotBePlacedException(String.format(ERROR_ODER_CANNOT_BE_PLACED_NO_PAYMENT_METHOD, id));
    }


}
