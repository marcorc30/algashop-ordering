package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.repository.Orders;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
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

    @Autowired
    public OrderPersistenceProvider(OrderPersistenceEntityRepository orderPersistenceEntityRepository, OrderPersistenceEntityAssembler orderPersistenceEntityAssembler) {
        this.orderPersistenceEntityRepository = orderPersistenceEntityRepository;
        this.orderPersistenceEntityAssembler = orderPersistenceEntityAssembler;
    }


    @Override
    public Optional<Order> ofId(OrderId orderId) {
        return Optional.empty();
    }

    @Override
    public boolean exists(OrderId orderId) {
        return false;
    }

    @Override
    public void add(Order aggregateRoot) {

        OrderPersistenceEntity persistenceEntity = orderPersistenceEntityAssembler.fromDomain(aggregateRoot);

        orderPersistenceEntityRepository.saveAndFlush(persistenceEntity);

    }

    @Override
    public int count() {
        return 0;
    }
}
