package com.algaworks.algashop.ordering.infrastructure.persistence.order;

import com.algaworks.algashop.ordering.domain.model.order.Order;
import com.algaworks.algashop.ordering.domain.model.order.Orders;
import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.domain.model.order.OrderId;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
Essa classe eh uma "ponte" dentre a camada de dominio e a camanda de infra (possui conexao com banco de dados)
Delega as chamdas para o spring data
 */

@Component
@Transactional(readOnly = true)
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
        return orderPersistenceEntityRepository.existsById(orderId.value().toLong());
    }

    @Override
    public long count() {
        return orderPersistenceEntityRepository.count();
    }

    @Override
    @Transactional(readOnly = false)
    public void add(Order aggregateRoot) {
        long orderId = aggregateRoot.id().value().toLong();

        orderPersistenceEntityRepository.findById(orderId).ifPresentOrElse(
                (persistenceEntity) -> update(aggregateRoot, persistenceEntity),
                () ->  insert(aggregateRoot)
        );

    }

    @Override
    public List<Order> placedByCustomerInYear(CustomerId customerId, Year year) {
        List<OrderPersistenceEntity> entities = orderPersistenceEntityRepository.placedByCustomerInYear(
                customerId.value(),
                year.getValue()
        );

        return entities.stream().map(orderPersistenceEntityDisassembler::toDomainEntity).collect(Collectors.toList());


    }

    @Override
    public Long salesQuantityByCustomerInYear(CustomerId customerId, Year year) {
        return orderPersistenceEntityRepository.salesQuantityByCustomerInYear(customerId.value(), year.getValue());
    }

    @Override
    public Money totalSouldForCustomer(CustomerId customerId) {
        BigDecimal totalForCustomer = orderPersistenceEntityRepository.totalSoldForCustomer(customerId.value());

        return new Money(totalForCustomer);
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

}
