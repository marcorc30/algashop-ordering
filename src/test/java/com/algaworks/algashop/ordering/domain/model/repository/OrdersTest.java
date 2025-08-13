package com.algaworks.algashop.ordering.domain.model.repository;


import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.provider.OrderPersistenceProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Optional;

/*
@DataJpaTest Escaneia apenas os beans de persistencia (JPA)
dessa forma, temos que importar manualmente o OrderPersistenceProvider
 */
@DataJpaTest
@Import({OrderPersistenceProvider.class, OrderPersistenceEntityAssembler.class, OrderPersistenceEntityDisassembler.class})
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


    @Test
    void deve_atualizar_um_pedido_existente(){

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        orders.add(order);

        order = orders.ofId(order.id()).orElseThrow();
        order.markAsPaid();

        orders.add(order);

        order = orders.ofId(order.id()).orElseThrow();

        Assertions.assertThat(order.isPaid()).isTrue();



    }

    @Test
    public void deve_nao_permitir_update_simultaneos(){
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        orders.add(order);

        Order orderT1 = orders.ofId(order.id()).orElseThrow();
        Order orderT2 = orders.ofId(order.id()).orElseThrow();

        orderT1.markAsPaid();
        orders.add(orderT1);

        orderT2.cancel();


        Assertions.assertThatExceptionOfType(ObjectOptimisticLockingFailureException.class)
                        .isThrownBy(() -> orders.add(orderT2));


        Order saveOrder = orders.ofId(order.id()).orElseThrow();

    }


    @Test
    public void deve_permitir_update_separados(){
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        orders.add(order);

        Order orderT1 = orders.ofId(order.id()).orElseThrow();
        orderT1.markAsPaid();
        orders.add(orderT1);


        Order orderT2 = orders.ofId(order.id()).orElseThrow();
        orderT2.cancel();
        orders.add(orderT2);

        Order saveOrder = orders.ofId(order.id()).orElseThrow();

        Assertions.assertThat(saveOrder.canceledAt()).isNotNull();
        Assertions.assertThat(saveOrder.paidAt()).isNotNull();

    }


}