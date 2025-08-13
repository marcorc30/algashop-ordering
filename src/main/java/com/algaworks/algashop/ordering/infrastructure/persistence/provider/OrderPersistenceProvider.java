package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.repository.Orders;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
Essa classe eh uma "ponte" dentre a camada de dominio e a camanda de infra (possui conexao com banco de dados)
Delega as chamdas para o spring data
 */

@Component
public class OrderPersistenceProvider implements Orders {

    private final OrderPersistenceEntityRepository orderPersistenceEntityRepository;

    private final OrderPersistenceEntityAssembler orderPersistenceEntityAssembler;
    private final OrderPersistenceEntityDisassembler orderPersistenceEntityDisassembler;

    private final EntityManager entityManager;

    @Autowired
    public OrderPersistenceProvider(OrderPersistenceEntityRepository orderPersistenceEntityRepository, OrderPersistenceEntityAssembler orderPersistenceEntityAssembler, OrderPersistenceEntityDisassembler orderPersistenceEntityDisassembler, EntityManager entityManager) {
        this.orderPersistenceEntityRepository = orderPersistenceEntityRepository;
        this.orderPersistenceEntityAssembler = orderPersistenceEntityAssembler;
        this.orderPersistenceEntityDisassembler = orderPersistenceEntityDisassembler;
        this.entityManager = entityManager;
    }


    @Override
    public Optional<Order> ofId(OrderId orderId) {
        Optional<OrderPersistenceEntity> orderPersistenceEntity = orderPersistenceEntityRepository.findById(orderId.value().toLong());

        Optional<Order> order = orderPersistenceEntity
                .map((orderPersistence) -> orderPersistenceEntityDisassembler
                        .toDomainEntity(orderPersistence));

        Order domainEntity = orderPersistenceEntityDisassembler.toDomainEntity(orderPersistenceEntity.get());

        return order;
    }

    @Override
    public boolean exists(OrderId orderId) {
        return false;
    }

    @Override
    public void add(Order aggregateRoot) {
        long orderId = aggregateRoot.id().value().toLong();

        orderPersistenceEntityRepository.findById(orderId).ifPresentOrElse(
                (persistenceEntity) -> {
                    update(aggregateRoot, persistenceEntity);
                },
                () -> {
                    insert(aggregateRoot);
                }


        );

    }

    private void update(Order aggregateRoot, OrderPersistenceEntity persistenceEntity) {
        persistenceEntity = orderPersistenceEntityAssembler.merge(persistenceEntity, aggregateRoot);
        entityManager.detach(persistenceEntity);
        persistenceEntity = orderPersistenceEntityRepository.saveAndFlush(persistenceEntity);
        aggregateRoot.setVersion(persistenceEntity.getVersion());
    }


    private void insert(Order aggregateRoot){
        OrderPersistenceEntity persistenceEntity = orderPersistenceEntityAssembler.fromDomain(aggregateRoot);
        orderPersistenceEntityRepository.saveAndFlush(persistenceEntity);

    }
    
    
    
    
    
    
    @Override
    public int count() {
        return 0;
    }
}
