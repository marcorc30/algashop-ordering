package com.algaworks.algashop.ordering.domain.model.service;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerRegistrationServiceTest {

    @Mock
    Customers customers;

    @InjectMocks
    CustomerRegistrationService customerRegistrationService;

    @Test
    public void shouldRegister(){

        Mockito.when(customers.isEmailUnique(Mockito.any(Email.class), Mockito.any(CustomerId.class)))
                .thenReturn(true);

        Customer customer = customerRegistrationService.register(
                new FullName("marco", "rodrigues"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("marco@email.com"),
                new Phone("48-256-2604"),
                new Document("255-08-0578"),
                true,
                Address.builder()
                        .street("teste")
                        .number("1134")
                        .neighborhood("teste")
                        .city("teste")
                        .state("teste")
                        .zipCode(new ZipCode("70253"))
                        .complement("apt 901")
                        .build()
        );

        Assertions.assertThat(customer.fullName()).isEqualTo(new FullName("marco","rodrigues"));
        Assertions.assertThat(customer.email()).isEqualTo(new Email("marco@email.com"));
    }



}