package com.algaworks.algashop.ordering.domain.model.service;

import com.algaworks.algashop.ordering.domain.exception.CantAddLoyaltPointsOrderIsNotReady;
import com.algaworks.algashop.ordering.domain.exception.OrderNotBelongsToCustomerException;
import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.valueobject.LoyaltPoints;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;

import java.util.Objects;

public class CustomerLoyaltyPointsService {

    private static final LoyaltPoints basePoints = new LoyaltPoints(5);
    private static final Money expectedAmountToGivePoints = new Money("1000");

    public void addPoints(Customer customer, Order order) {
        Objects.requireNonNull(customer);
        Objects.requireNonNull(order);

        if (!customer.id().equals(order.customerId())){
            throw new OrderNotBelongsToCustomerException();
        }

        if (!order.isRead()){
            throw new CantAddLoyaltPointsOrderIsNotReady();
        }

        customer.addLoyaltyPoints(calculatePoints(order));

    }

    private LoyaltPoints calculatePoints(Order order) {
        if (shouldGivePointsByAmount(order.totalAmount())){
            Money result = order.totalAmount().divide(expectedAmountToGivePoints);
            return new LoyaltPoints(result.value().intValue() * basePoints.value());
        }
        return LoyaltPoints.ZERO;
    }

    private boolean shouldGivePointsByAmount(Money amount) {
        return amount.compareTo(expectedAmountToGivePoints.value()) >= 0;
    }
}
