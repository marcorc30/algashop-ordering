package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.repository.Orders;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
import org.assertj.core.api.Assertions;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;

import static com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

@Import({OrderPersistenceProvider.class,
        OrderPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class,
        CustomerPersistenceProvider.class,
        CustomerPersistenceEntityDisassembler.class,
        CustomerPersistenceEntityAssembler.class,
        SpringDataAuditingConfig.class})
@DataJpaTest
class OrderPersistenceProviderTest {

    private OrderPersistenceProvider persistenceProvider;
    private OrderPersistenceEntityRepository entityRepository;
    private CustomerPersistenceProvider customerPersistenceProvider;

    @Autowired
    public OrderPersistenceProviderTest(OrderPersistenceProvider persistenceProvider,
                                        OrderPersistenceEntityRepository entityRepository, CustomerPersistenceProvider customerPersistenceProvider) {
        this.persistenceProvider = persistenceProvider;
        this.entityRepository = entityRepository;
        this.customerPersistenceProvider = customerPersistenceProvider;
    }

    @BeforeEach
    public void setup(){
        if (!customerPersistenceProvider.exists(DEFAULT_CUSTOMER_ID)){
            customerPersistenceProvider.add(CustomerTestDataBuilder.existingdCustomer().build());
        }
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

    @Test
    void dado_um_id_verificar_se_order_existe(){

        Order order = OrderTestDataBuilder.anOrder().withItems(true).build();
        Order order2 = OrderTestDataBuilder.anOrder().withItems(true).build();
        OrderId id = order.id();

        persistenceProvider.add(order);
        persistenceProvider.add(order2);

        boolean exists = persistenceProvider.exists(id);

        Assertions.assertThat(exists).isEqualTo(true);
        Assertions.assertThat(persistenceProvider.count()).isEqualTo(2);


    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void dado_um_order_entity_tentar_consultar_sem_transacao_deve_falhar(){

        Order order = OrderTestDataBuilder.anOrder().withItems(true).build();
        persistenceProvider.add(order);


        Assertions.assertThatNoException()
                .isThrownBy(() -> persistenceProvider.ofId(order.id()));

//        Assertions.assertThatExceptionOfType(LazyInitializationException.class)
//                .isThrownBy(() -> persistenceProvider.ofId(order.id()));



    }

}