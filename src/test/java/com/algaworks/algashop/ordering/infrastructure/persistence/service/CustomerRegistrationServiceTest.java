package com.algaworks.algashop.ordering.infrastructure.persistence.service;

import com.algaworks.algashop.ordering.domain.model.commons.*;
import com.algaworks.algashop.ordering.domain.model.customer.BirthDate;
import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.Customers;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerRegistrationService;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class CustomerRegistrationServiceTest {

    @Mock
    Customers customers;

    @InjectMocks
    CustomerRegistrationService customerRegistrationService;

    @Test
    void registrarCustomer(){
        Mockito.when(customers.isEmailUnique(Mockito.any(Email.class), Mockito.any(CustomerId.class)))
                .thenReturn(true);

        Customer customer = customerRegistrationService.register(
                new FullName("marco", "aurelio"),
                new BirthDate(LocalDate.of(1991, 4, 7)),
                new Email("teste@gmail.com"),
                new Phone("48-256-2604"),
                new Document("255-08-058"),
                true,
                Address.builder()
                        .complement("teste complement")
                        .state("teste state")
                        .zipCode(new ZipCode("55555"))
                        .street("street teste")
                        .number("10")
                        .neighborhood("teste")
                        .city("teste city")
                        .build()
        );

        Assertions.assertThat(customer.email().value()).isEqualTo("teste@gmail.com");

    }




}
