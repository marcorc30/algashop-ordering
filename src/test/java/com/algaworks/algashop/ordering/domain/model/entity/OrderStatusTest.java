package com.algaworks.algashop.ordering.domain.model.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderStatusTest {

    @Test
    void alterarStatus(){
        Assertions.assertThat(OrderStatus.PAID.canNotChangeToStatus(OrderStatus.PLACED)).isTrue();
        Assertions.assertThat(OrderStatus.PLACED.canChangeToStatus(OrderStatus.PAID)).isTrue();
    }

}