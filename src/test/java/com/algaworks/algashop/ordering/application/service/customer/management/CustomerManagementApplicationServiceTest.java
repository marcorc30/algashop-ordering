package com.algaworks.algashop.ordering.application.service.customer.management;

import com.algaworks.algashop.ordering.application.customer.management.CustomerManagementApplicationService;
import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.application.customer.management.CustomerInput;
import com.algaworks.algashop.ordering.application.customer.management.CustomerOutput;
import com.algaworks.algashop.ordering.application.customer.management.CustomerUpdateInput;
import com.algaworks.algashop.ordering.application.utility.Mapper;
import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityDisassembler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;


@SpringBootTest
@Transactional
//@Import(CustomerPersistenceEntityDisassembler.class)
class CustomerManagementApplicationServiceTest {

    @Autowired
    CustomerManagementApplicationService customerService;

    @Autowired
    Mapper mapper;


    @Test
    public void create(){

        CustomerInput customerInput = CustomerInputTestDataBuilder.aCustomer().build();

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

//    @Test
    public void update(){

        CustomerInput customerInput = CustomerInputTestDataBuilder.aCustomer().build();
        CustomerUpdateInput customerUpdateInput = CustomerUpdateInputTestDataBuilder.aUpdateCustomer().build();

        UUID uuid = customerService.create(customerInput);

        Assertions.assertThat(uuid).isNotNull();

        customerService.update(uuid, customerUpdateInput);

        CustomerOutput customerOutput = customerService.findById(uuid);

        Assertions.assertThat(customerOutput.getPhone()).isEqualTo("555-555-666");


    }

//    @Test
    public void shouldArchive(){

        CustomerInput customerInput = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerService.create(customerInput);

        Assertions.assertThat(customerId).isNotNull();

        customerService.archive(customerId);

        CustomerOutput archivedCustomer = customerService.findById(customerId);

        Assertions.assertThat(archivedCustomer)
                .isNotNull()
                .extracting(
                        CustomerOutput::getFirstName,
                        CustomerOutput::getLastName,
                        CustomerOutput::getPhone,
                        CustomerOutput::getDocument
                ).containsExactly(
                        "Anonymous",
                        "Anonymous",
                        "000-000-0000",
                        "000-00-0000"
                );


    }




}