package com.algaworks.algashop.ordering.infrastructure.persistence.disassembler;

import com.algaworks.algashop.ordering.domain.model.commons.*;
import com.algaworks.algashop.ordering.domain.model.customer.BirthDate;
import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;

public class CustomerPersistenceEntityDisassembler {

    public Customer toDomainEntity(CustomerPersistenceEntity persistenceEntity){

        var address = getAddress(persistenceEntity);


        return Customer.brandNew()
                .phone(new Phone(persistenceEntity.getPhone()))
                .email(new Email(persistenceEntity.getEmail()))
                .birthDate(new BirthDate(persistenceEntity.getBirthDate()))
                .document(new Document(persistenceEntity.getDocument()))
                .fullName(new FullName(persistenceEntity.getFirstName(), persistenceEntity.getLastName()))
                .promotionNotificationsAllowed(persistenceEntity.getPromotionNotificationsAllowed())
                .address(address)
                .build();

    }

    private static Address getAddress(CustomerPersistenceEntity persistenceEntity) {
        return Address.builder()
                .city(persistenceEntity.getAddress().getCity())
                .state(persistenceEntity.getAddress().getState())
                .neighborhood(persistenceEntity.getAddress().getNeighborhood())
                .street(persistenceEntity.getAddress().getStreet())
                .zipCode(new ZipCode(persistenceEntity.getAddress().getZipCode()))
                .number(persistenceEntity.getAddress().getNumber())
                .complement(persistenceEntity.getAddress().getComplement())
                .build();
    }


}
