package com.algaworks.algashop.ordering.application.service.customer.management;

import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.application.customer.management.CustomerUpdateInput;

public class CustomerUpdateInputTestDataBuilder {

    public static CustomerUpdateInput.CustomerUpdateInputBuilder aUpdateCustomer(){
        return CustomerUpdateInput.builder()
                .firstName("teste2")
                .lastName("da silva2")
                .promotionNotificationsAllowed(true)
                .phone("555-555-666")
                .address(AddressData.builder()
                        .street("street2")
                        .city("city2")
                        .complement("complement2")
                        .zipCode("66666")
                        .number("66666")
                        .neighborhood("teste2")
                        .state("state2")
                        .build());
    }

}
