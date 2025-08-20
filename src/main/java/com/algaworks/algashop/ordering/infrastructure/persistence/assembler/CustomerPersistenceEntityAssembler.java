package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerPersistenceEntityAssembler {

    public CustomerPersistenceEntity fromDomain(Customer customer){
        return this.merge(new CustomerPersistenceEntity(), customer);
    }

    public CustomerPersistenceEntity merge(CustomerPersistenceEntity persistenceEntity, Customer customer){

        persistenceEntity.setId(customer.id().value());
        persistenceEntity.setArchived(customer.isArchived());
        persistenceEntity.setAddress(address(customer));
        persistenceEntity.setDocument(customer.document().value());
        persistenceEntity.setEmail(customer.email().value());
        persistenceEntity.setArchivedAt(customer.archivedAt());
        persistenceEntity.setBirthDate(customer.birthDate().value());
        persistenceEntity.setLoyaltPoints(customer.loyaltPoints().value());
        persistenceEntity.setPromotionNotificationsAllowed(customer.isPromotionNotificationsAllowed());
        persistenceEntity.setRegisteredAt(customer.registeredAt());
        persistenceEntity.setFirstName(customer.fullName().firstName());
        persistenceEntity.setLastName(customer.fullName().lastName());
        persistenceEntity.setPhone(customer.phone().value());

        return persistenceEntity;
    }

    public AddressEmbeddable address(Customer customer){
        return AddressEmbeddable.builder()
                .state(customer.address().state())
                .city(customer.address().city())
                .complement(customer.address().complement())
                .zipCode(customer.address().zipCode().value())
                .street(customer.address().street())
                .neighborhood(customer.address().neighborhood())
                .build();

    }

}
