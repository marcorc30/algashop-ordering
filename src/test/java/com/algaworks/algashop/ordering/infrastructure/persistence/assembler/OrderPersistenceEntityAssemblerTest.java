package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

class OrderPersistenceEntityAssemblerTest {

    private final OrderPersistenceEntityAssembler assembler = new OrderPersistenceEntityAssembler();

//    @Autowired
//    OrderPersistenceEntityAssemblerTest(OrderPersistenceEntityAssembler assembler) {
//        this.assembler = assembler;
//    }

    @Test
    void convert_domain_to_entity(){

        Order order = OrderTestDataBuilder.anOrder().build();
        OrderPersistenceEntity orderPersistence = assembler.fromDomain(order);


        Assertions.assertThat(order.id().value().toLong()).isEqualTo(orderPersistence.getId());
        Assertions.assertThat(order.paymentMethod().name()).isEqualTo(orderPersistence.getPaymentMethod());


    }

}