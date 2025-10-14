package com.algaworks.algashop.ordering.infrastructure.persistence.customer;

import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.Customers;
import com.algaworks.algashop.ordering.domain.model.commons.Email;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerPersistenceProvider implements Customers {

    private final CustomerPersistenceEntityRepository repository;
    private final CustomerPersistenceEntityAssembler assembler;
    private final CustomerPersistenceEntityDisassembler disassembler;

    private final EntityManager entityManager;


    @Autowired
    public CustomerPersistenceProvider(CustomerPersistenceEntityRepository repository,
                                       CustomerPersistenceEntityAssembler assembler,
                                       CustomerPersistenceEntityDisassembler disassembler,
                                       EntityManager entityManager) {
        this.repository = repository;
        this.assembler = assembler;
        this.disassembler = disassembler;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Customer> ofId(CustomerId customerId) {
//        Objects.requireNonNull(customerId);
//        Optional<CustomerPersistenceEntity> customerPersistenceEntity = repository.findById(customerId.value());
//
//        Optional<Customer> customer = customerPersistenceEntity.map(cp -> disassembler.toDomainEntity(cp));
//        return customer;

        return repository.findById(customerId.value())
                .map(disassembler::toDomainEntity);


    }

    @Override
    public boolean exists(CustomerId customerId) {

        return repository.existsById(customerId.value());
    }

    @Override
    public void add(Customer aggregateRoot) {

        UUID id = aggregateRoot.id().value();
        
        repository.findById(id).ifPresentOrElse(
                (customerPersistenceEntity) -> update(aggregateRoot, customerPersistenceEntity ),
                () -> insert(aggregateRoot)
                
        );


    }

    private void update(Customer aggregateRoot, CustomerPersistenceEntity persistenceEntity){
        persistenceEntity = assembler.merge(persistenceEntity, aggregateRoot);
        entityManager.detach(persistenceEntity);
        persistenceEntity = repository.saveAndFlush(persistenceEntity);
        aggregateRoot.setVersion(persistenceEntity.getVersion());

    }

    private void insert(Customer aggregateRoot) {
        CustomerPersistenceEntity customerPersistenceEntity = assembler.fromDomain(aggregateRoot);
        repository.saveAndFlush(customerPersistenceEntity);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<Customer> ofEmail(Email email) {
        return repository.findByEmail(email.value()).map(disassembler::toDomainEntity);
    }

    @Override
    public boolean isEmailUnique(Email email, CustomerId exceptCustomerId) {
        return !repository.existsByEmailAndIdNot(email.value(), exceptCustomerId.value());
    }
}
