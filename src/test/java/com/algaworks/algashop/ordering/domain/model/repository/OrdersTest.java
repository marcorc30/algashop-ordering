package com.algaworks.algashop.ordering.domain.model.repository;


import com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.provider.CustomerPersistenceProvider;
import com.algaworks.algashop.ordering.infrastructure.persistence.provider.OrderPersistenceProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/*
@DataJpaTest Escaneia apenas os beans de persistencia (JPA)
dessa forma, temos que importar manualmente o OrderPersistenceProvider
 */
@DataJpaTest
@Import({OrderPersistenceProvider.class,
        OrderPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class,
        CustomerPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class
})
//@SpringBootTest
class OrdersTest {


    private Orders orders;
    private Customers customers;

    @Autowired
    public OrdersTest(Orders orders, Customers customers) {
        this.orders = orders;
        this.customers = customers;
    }

    @BeforeEach
    public void setup(){
        if (!customers.exists(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)){
            customers.add(CustomerTestDataBuilder.existingdCustomer().build());
        }
    }

    @Test
    public void shouldPersistAndFind() {
        Order originalOrder = OrderTestDataBuilder.anOrder().build();
        OrderId orderId = originalOrder.id();
        orders.add(originalOrder);

        Optional<Order> possibleOrder = orders.ofId(orderId);

        assertThat(possibleOrder).isPresent();

        Order savedOrder = possibleOrder.get();

        assertThat(savedOrder).satisfies(
                s -> assertThat(s.id()).isEqualTo(orderId),
                s -> assertThat(s.customerId()).isEqualTo(originalOrder.customerId()),
                s -> assertThat(s.totalAmount()).isEqualTo(originalOrder.totalAmount()),
                s -> assertThat(s.placedAt()).isEqualTo(originalOrder.placedAt()),
                s -> assertThat(s.paidAt()).isEqualTo(originalOrder.paidAt()),
                s -> assertThat(s.canceledAt()).isEqualTo(originalOrder.canceledAt()),
                s -> assertThat(s.readyAt()).isEqualTo(originalOrder.readyAt()),
                s -> assertThat(s.status()).isEqualTo(originalOrder.status()),
                s -> assertThat(s.paymentMethod()).isEqualTo(originalOrder.paymentMethod())
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

        assertThat(order.isPaid()).isTrue();



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

        assertThat(saveOrder.canceledAt()).isNotNull();
        assertThat(saveOrder.paidAt()).isNotNull();

    }

    @Test
    public void deveListarPedidosPorAno(){

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build());

        CustomerId customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

        List<Order> ordersList = orders.placedByCustomerInYear(customerId, Year.now());

        Assertions.assertThat(ordersList.size()).isEqualTo(2);

    }


    @Test
    public void deveContarTotalDePedidosPorCliente(){

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build());

        CustomerId customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

        Assertions.assertThat(orders.salesQuantityByCustomerInYear(customerId, Year.now())).isEqualTo(2);



    }

    @Test
    public void deveSomarOsValoresDosPedidosPorCliente(){

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build());

        CustomerId customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

        Assertions.assertThat(orders.totalSouldForCustomer(customerId).value()).isEqualTo(new BigDecimal("20.00"));



    }


}