package com.algaworks.algashop.ordering.domain.model.order;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {
    DRAFT,
    PLACED(DRAFT),
    PAID(PLACED),
    READY(PAID),
    CANCELED(DRAFT, PLACED, PAID);

    private List<OrderStatus> previousStatus;

    OrderStatus(OrderStatus... previousStatus){
        this.previousStatus = Arrays.asList(previousStatus);
    }

    public boolean canChangeToStatus(OrderStatus newStatus){

        OrderStatus currentStatus = this;
        return newStatus.previousStatus.contains(currentStatus);

    }

    public boolean canNotChangeToStatus(OrderStatus newStatus){
        return !canChangeToStatus(newStatus);
    }


}
