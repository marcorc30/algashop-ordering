package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import com.algaworks.algashop.ordering.domain.model.utility.IdGenerator;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;

import java.time.LocalDate;

public class CustomerPersistenceEntityTestDataBuilder {

    public CustomerPersistenceEntityTestDataBuilder() {
    }

    public static CustomerPersistenceEntity.CustomerPersistenceEntityBuilder exiting(){

        return CustomerPersistenceEntity.builder()
                .id(IdGenerator.generateTimeBaseUUID())
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
