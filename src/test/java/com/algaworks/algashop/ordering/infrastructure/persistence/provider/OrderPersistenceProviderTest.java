package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.repository.Orders;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.beans.PropertyDescriptor;

@Import({OrderPersistenceProvider.class, OrderPersistenceEntityAssembler.class})
@DataJpaTest
class OrderPersistenceProviderTest {

    private final Orders orders;

    @Autowired
    OrderPersistenceProviderTest(Orders oders) {
        this.orders = oders;
    }



    @Test
    public void shouldPersistenceAndFlush(){
        Order orderOriginal = OrderTestDataBuilder.anOrder().build();
        orders.add(orderOriginal);

        Assertions.assertThat(orders.ofId(orderOriginal.id())).isPresent();


    }

}