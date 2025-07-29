package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;

import java.util.HashSet;

public class OrderTestDataBuilder {

    public OrderTestDataBuilder() {
    }

    public static OrderTestDataBuilder anOrder(){
        return new OrderTestDataBuilder();
    }


}
