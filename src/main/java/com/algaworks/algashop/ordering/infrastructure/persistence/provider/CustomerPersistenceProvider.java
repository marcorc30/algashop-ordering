package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Optional;

public class CustomerPersistenceProvider implements Customers {

    private CustomerPersistenceEntityRepository repository;

    @Autowired
    private CustomerPersistenceEntityAssembler assembler;
    private CustomerPersistenceEntityDisassembler disassembler;

    @Override
    public Optional<Customer> ofId(CustomerId customerId) {
        Objects.requireNonNull(customerId);
        Optional<Customer> customer = repository.findById(customerId.value())
                .map(cp -> disassembler.toDomainEntity(cp));

        return customer;
    }

    @Override
    public boolean exists(CustomerId customerId) {

        return repository.existsById(customerId.value());
    }

    @Override
    public void add(Customer aggregateRoot) {
        
        long id = aggregateRoot.id().toLong();

        CustomerPersistenceEntity customerPersistenceEntity = assembler.fromDomain(aggregateRoot);
        repository.saveAndFlush(customerPersistenceEntity);

    }

    @Override
    public long count() {
        return repository.count();
    }
}
