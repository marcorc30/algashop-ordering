package com.algaworks.algashop.ordering.domain.model.repository;


import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.provider.OrderPersistenceProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

/*
@DataJpaTest Escaneia apenas os beans de persistencia (JPA)
dessa forma, temos que importar manualmente o OrderPersistenceProvider
 */
@DataJpaTest
@Import({OrderPersistenceProvider.class, OrderPersistenceEntityAssembler.class})
//@SpringBootTest
class OrdersTest {


    private Orders orders;

    @Autowired
    public OrdersTest(Orders orders) {
        this.orders = orders;
    }

    @Test
    public void deve_persistir_e_localizar(){
        
        Order order = OrderTestDataBuilder.anOrder().build();
        OrderId orderId = order.id();
        orders.add(order);

        Order orderSalvo = orders.ofId(orderId).get();

        Assertions.assertWith(orderSalvo,
                os -> Assertions.assertThat(orderId).isEqualTo(orderSalvo.id())

        );


    }



}