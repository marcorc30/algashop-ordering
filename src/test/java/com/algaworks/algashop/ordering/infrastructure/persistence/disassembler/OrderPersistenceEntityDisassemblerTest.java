package com.algaworks.algashop.ordering.infrastructure.persistence.disassembler;

import com.algaworks.algashop.ordering.domain.model.order.Order;
import com.algaworks.algashop.ordering.domain.model.order.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.order.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.OrderPersistenceEntityDisassembler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

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

    @Test
    void dado_um_entity_sem_items_entao_remover_items_do_model(){

        OrderPersistenceEntity persistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder().items(new HashSet<>()).build();
        Order order = OrderTestDataBuilder.anOrder().withItems(true).build();

        Assertions.assertThat(persistenceEntity.getItems()).isEmpty();
        Assertions.assertThat(order.items()).isNotEmpty();

        Order domainEntity = disassembler.toDomainEntity(persistenceEntity);

        Assertions.assertThat(domainEntity.items()).isEmpty();



    }

    @Test
    void dado_um_entity_com_items_entao_adicionar_items_no_domain(){

        OrderPersistenceEntity persistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();
        Order order = OrderTestDataBuilder.anOrder().withItems(false).build();

        Assertions.assertThat(persistenceEntity.getItems().size()).isEqualTo(2);
        Assertions.assertThat(order.items().size()).isEqualTo(0);

        Order domainModel = disassembler.toDomainEntity(persistenceEntity);

        Assertions.assertThat(domainModel.items()).isNotEmpty();
        Assertions.assertThat(domainModel.items().size()).isEqualTo(2);



    }
}