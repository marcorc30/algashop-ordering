package com.algaworks.algashop.ordering.domain.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    void alterarStatus(){
        Assertions.assertThat(OrderStatus.PAID.canNotChangeToStatus(OrderStatus.PLACED)).isTrue();
        Assertions.assertThat(OrderStatus.PLACED.canChangeToStatus(OrderStatus.PAID)).isTrue();
    }

}