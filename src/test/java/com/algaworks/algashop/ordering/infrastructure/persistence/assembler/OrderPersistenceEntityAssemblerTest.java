package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderItem;
import com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.*;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class OrderPersistenceEntityAssemblerTest {


    @Mock
    private CustomerPersistenceEntityRepository customerRepository;

    @InjectMocks
    private OrderPersistenceEntityAssembler assembler;

    @BeforeEach
    public void setup(){
        Mockito.when(customerRepository.getReferenceById(Mockito.any(UUID.class)))
                .then(a -> {
                    UUID customerId = a.getArgument(0, UUID.class);
                    CustomerPersistenceEntity entity = CustomerPersistenceEntityTestDataBuilder.exiting().id(customerId).build();
                    return entity;

                });
    }

    @Test
    void convert_domain_to_entity(){

        Order order = OrderTestDataBuilder.anOrder().build();
        OrderPersistenceEntity orderPersistence = assembler.fromDomain(order);


        Assertions.assertThat(order.id().value().toLong()).isEqualTo(orderPersistence.getId());
        Assertions.assertThat(order.paymentMethod().name()).isEqualTo(orderPersistence.getPaymentMethod());
        Assertions.assertThat(order.billing().email().value()).isEqualTo(orderPersistence.getBilling().getEmail());
        Assertions.assertThat(order.billing().document().value()).isEqualTo(orderPersistence.getBilling().getDocument());


    }

    @Test
    void dado_um_pedido_sem_items_entao_remover_items_modelo_persistencia(){

        Order order = OrderTestDataBuilder.anOrder().withItems(false).build();
        OrderPersistenceEntity orderPersistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();

        Assertions.assertThat(order.items()).isEmpty();
        Assertions.assertThat(orderPersistenceEntity.getItems()).isNotEmpty();

        assembler.merge(orderPersistenceEntity, order);

        Assertions.assertThat(order.items()).isEmpty();
        Assertions.assertThat(orderPersistenceEntity.getItems()).isEmpty();


    }

    @Test
    void dado_um_pedido_com_itens_entao_adicionar_items_na_entity(){
        Order order = OrderTestDataBuilder.anOrder().withItems(true).build();
        OrderPersistenceEntity orderPersistence = OrderPersistenceEntityTestDataBuilder.existingOrder().items(new HashSet<>()).build();

        Assertions.assertThat(order.items().size()).isEqualTo(2);
        Assertions.assertThat(orderPersistence.getItems().size()).isEqualTo(0);

        assembler.merge(orderPersistence, order);

        Assertions.assertThat(order.items().size()).isEqualTo(2);
        Assertions.assertThat(orderPersistence.getItems().size()).isEqualTo(2);

    }

    @Test
    void dado_um_pedido_com_items_e_um_pedido_entity_com_items_atualizar_o_entity(){
        Order order = OrderTestDataBuilder.anOrder().withItems(true).build();
        Assertions.assertThat(order.items().size()).isEqualTo(2);

        Set<OrderItemPersistenceEntity> itemPersistenceEntities = order.items()
                .stream()
                .map(i -> assembler.fromDomain(i))
                .collect(Collectors.toSet());

        OrderPersistenceEntity persistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder()
                .items(itemPersistenceEntities)
                .build();

        OrderItem orderItem = order.items().iterator().next();

        order.removeItem(orderItem.id());

        assembler.merge(persistenceEntity, order);

        Assertions.assertThat(persistenceEntity.getItems()).isNotEmpty();
        Assertions.assertThat(persistenceEntity.getItems().size()).isEqualTo(order.items().size());



    }

}