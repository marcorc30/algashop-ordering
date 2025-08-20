package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CustomerPersistenceProvider implements Customers {

    CustomerPersistenceEntityRepository repository;

    @Autowired
    CustomerPersistenceEntityAssembler assembler;

    @Override
    public Optional<Customer> ofId(CustomerId customerId) {



        return Optional.empty();
    }

    @Override
    public boolean exists(CustomerId customerId) {
        return false;
    }

    @Override
    public void add(Customer aggregateRoot) {

        CustomerPersistenceEntity customerPersistenceEntity = assembler.fromDomain(aggregateRoot);
        repository.saveAndFlush(customerPersistenceEntity);

    }

    @Override
    public long count() {
        return 0;
    }
}
