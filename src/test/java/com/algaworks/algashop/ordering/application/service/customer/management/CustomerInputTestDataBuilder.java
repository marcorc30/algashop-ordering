package com.algaworks.algashop.ordering.application.service.customer.management;

import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.application.customer.management.CustomerInput;

import java.time.LocalDate;

public class CustomerInputTestDataBuilder {

    public static CustomerInput.CustomerInputBuilder aCustomer(){
        return CustomerInput.builder()
                .email("teste@gmail.com")
                .firstName("teste")
                .lastName("da silva")
                .promotionNotificationsAllowed(true)
                .document("12346")
                .phone("555-555-555")
                .birthDate(LocalDate.now().minusYears(10))
                .address(AddressData.builder()
                        .street("street")
                        .city("city")
                        .complement("complement")
                        .zipCode("55555")
                        .number("55555")
                        .neighborhood("teste")
                        .state("state")
                        .build());
    }
}
