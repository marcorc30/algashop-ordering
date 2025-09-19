package com.algaworks.algashop.ordering.domain.model.service;


import com.algaworks.algashop.ordering.domain.model.entity.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.LoyaltPoints;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerLoyaltyPointsServiceTest {

    CustomerLoyaltyPointsService customerLoyaltyPointsService = new CustomerLoyaltyPointsService();

    @Test
    void deveValidarClienteEPedidoQuandoAdicionarPontosAcumulados(){
        Customer customer = CustomerTestDataBuilder.existingdCustomer().build();
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.READY).build();

        customerLoyaltyPointsService.addPoints(customer, order);

        Assertions.assertThat(customer.id().value()).isEqualTo(order.customerId().value());
        Assertions.assertThat(customer.loyaltPoints()).isEqualTo(new LoyaltPoints(30));

    }

}