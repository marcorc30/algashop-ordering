package com.algaworks.algashop.ordering.application.service.customer.management;

import com.algaworks.algashop.ordering.application.customer.management.CustomerManagementApplicationService;
import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.application.customer.management.CustomerInput;
import com.algaworks.algashop.ordering.application.customer.management.CustomerOutput;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityDisassembler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.UUID;


@SpringBootTest
@Import(CustomerPersistenceEntityDisassembler.class)
class CustomerManagementApplicationServiceTest {

    @Autowired
    CustomerManagementApplicationService customerService;


    @Test
    public void create(){

        CustomerInput customerInput = CustomerInput.builder()
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
                        .build())
                .build();

        UUID customerId = customerService.create(customerInput);

        Assertions.assertThat(customerId).isNotNull();

        CustomerOutput customerOutput = customerService.findById(customerId);

        Assertions.assertThat(customerOutput.getId()).isEqualTo(customerId);
        Assertions.assertThat(customerOutput.getFirstName()).isEqualTo("teste");
        Assertions.assertThat(customerOutput.getLastName()).isEqualTo("da silva");
        Assertions.assertThat(customerOutput.getPhone()).isEqualTo("555-555-555");
        Assertions.assertThat(customerOutput.getEmail()).isEqualTo("teste@gmail.com");
        Assertions.assertThat(customerOutput.getAddress().getState()).isEqualTo("state");


    }


}