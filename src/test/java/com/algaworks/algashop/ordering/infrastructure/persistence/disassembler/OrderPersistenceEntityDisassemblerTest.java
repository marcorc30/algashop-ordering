package com.algaworks.algashop.ordering.infrastructure.persistence.disassembler;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderPersistenceEntityDisassemblerTest {

    OrderPersistenceEntityDisassembler disassembler = new OrderPersistenceEntityDisassembler();

    @Test
    void convertPersistenceModelToDomainEntity(){
        OrderPersistenceEntity persistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();
        Order domainEntity = disassembler.toDomainEntity(persistenceEntity);

        Assertions.assertThat(domainEntity).satisfies(
                s -> Assertions.assertThat(s.id()).isEqualTo(new OrderId(persistenceEntity.getId())),
                s -> Assertions.assertThat(s.status().name()).isEqualTo(persistenceEntity.getStatus())
        );
    }
}