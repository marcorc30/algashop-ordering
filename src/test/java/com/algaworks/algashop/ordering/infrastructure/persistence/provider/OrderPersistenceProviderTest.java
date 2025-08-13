package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.repository.Orders;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.beans.PropertyDescriptor;

@Import({OrderPersistenceProvider.class,
        OrderPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class,
        SpringDataAuditingConfig.class})
@DataJpaTest
class OrderPersistenceProviderTest {

    private OrderPersistenceProvider persistenceProvider;
    private OrderPersistenceEntityRepository entityRepository;

    @Autowired
    public OrderPersistenceProviderTest(OrderPersistenceProvider persistenceProvider,
                                        OrderPersistenceEntityRepository entityRepository) {
        this.persistenceProvider = persistenceProvider;
        this.entityRepository = entityRepository;
    }

    @Test
    public void shouldUpdateAndKeepPersistenceEntityState(){
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        long orderId = order.id().value().toLong();
        persistenceProvider.add(order);

        var orderPersistenceEntity = entityRepository.findById(orderId).orElseThrow();

        Assertions.assertThat(orderPersistenceEntity.getStatus()).isEqualTo(OrderStatus.PLACED.name());

        Assertions.assertThat(orderPersistenceEntity.getLastModifiedByUserId()).isNotNull();
        Assertions.assertThat(orderPersistenceEntity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(orderPersistenceEntity.getCreateByUserId()).isNotNull();


        order = persistenceProvider.ofId(order.id()).orElseThrow();
        order.markAsPaid();
        persistenceProvider.add(order);
        var orderPersistenceEntity1 = entityRepository.findById(orderId).orElseThrow();



        Assertions.assertThat(orderPersistenceEntity1.getStatus()).isEqualTo(OrderStatus.PAID.name());

        Assertions.assertThat(orderPersistenceEntity1.getLastModifiedByUserId()).isNotNull();
        Assertions.assertThat(orderPersistenceEntity1.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(orderPersistenceEntity1.getCreateByUserId()).isNotNull();
    }

}