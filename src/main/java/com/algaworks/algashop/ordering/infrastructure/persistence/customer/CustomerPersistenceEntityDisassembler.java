package com.algaworks.algashop.ordering.infrastructure.persistence.customer;

import com.algaworks.algashop.ordering.domain.model.commons.*;
import com.algaworks.algashop.ordering.domain.model.customer.BirthDate;
import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.domain.model.customer.LoyaltPoints;

public class CustomerPersistenceEntityDisassembler {

    public Customer toDomainEntity(CustomerPersistenceEntity persistenceEntity){

        var address = getAddress(persistenceEntity);


        return Customer.existing()
                .id(new CustomerId(persistenceEntity.getId()))
                .fullName(new FullName(persistenceEntity.getFirstName(), persistenceEntity.getLastName()))
                .birthDate(new BirthDate(persistenceEntity.getBirthDate()))
                .email(new Email(persistenceEntity.getEmail()))
                .phone(new Phone(persistenceEntity.getPhone()))
                .document(new Document(persistenceEntity.getDocument()))
                .loyaltyPoints(new LoyaltPoints(persistenceEntity.getLoyaltPoints()))
                .promotionNotificationsAllowed(persistenceEntity.getPromotionNotificationsAllowed())
                .archived(persistenceEntity.getArchived())
                .registeredAt(persistenceEntity.getRegisteredAt())
                .archivedAt(persistenceEntity.getArchivedAt())
                .address(address)
                .version(persistenceEntity.getVersion())
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
