package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.commons.AddressEmbeddable;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

public class CustomerPersistenceEntityTestDataBuilder {

    public CustomerPersistenceEntityTestDataBuilder() {
    }

    public static CustomerPersistenceEntity.CustomerPersistenceEntityBuilder aCustomer() {
        return CustomerPersistenceEntity.builder()
                .id(DEFAULT_CUSTOMER_ID.value())
                .registeredAt(OffsetDateTime.now())
                .promotionNotificationsAllowed(true)
                .archived(false)
                .archivedAt(null)
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1991, 7,5))
                .email("johndoe@email.com")
                .phone("478-256-2604")
                .document("255-08-0578")
                .promotionNotificationsAllowed(true)
                .address(AddressEmbeddable.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("North Ville")
                        .city("York")
                        .state("South California")
                        .zipCode("12345")
                        .complement("Apt. 114")
                        .build());
    }

    public static CustomerPersistenceEntity.CustomerPersistenceEntityBuilder exiting(){

        return CustomerPersistenceEntity.builder()
                .id(DEFAULT_CUSTOMER_ID.value())
                .email("marcorc30@gmail.com")
                .archived(false)
                .phone("555-555-555")
                .birthDate(LocalDate.now().minusYears(20))
                .lastName("marco")
                .firstName("sobrenome")
                .document("564464")
                .archived(false)
                .loyaltPoints(1000)
                .promotionNotificationsAllowed(true)
                .address(getAddress());

    }

    private static AddressEmbeddable getAddress(){

        return AddressEmbeddable.builder()
                .city("guara")
                .state("df")
                .neighborhood("teste")
                .number("10002")
                .zipCode("55555")
                .complement("complemento")
                .street("street")
                .build();

    }
}
