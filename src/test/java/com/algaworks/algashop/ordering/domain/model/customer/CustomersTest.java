package com.algaworks.algashop.ordering.domain.model.customer;

import com.algaworks.algashop.ordering.domain.model.commons.Email;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.provider.CustomerPersistenceProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@Import({CustomerPersistenceProvider.class, CustomerPersistenceEntityAssembler.class, CustomerPersistenceEntityDisassembler.class})
public class CustomersTest {

    private Customers customers;


    @Autowired
    public CustomersTest(Customers customers) {
        this.customers = customers;
    }

    @Test
    public void localizarPorEmail(){
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        Optional<Customer> customer1 = customers.ofEmail(customer.email());

        Assertions.assertThat(customer1).isPresent();

    }

    @Test
    public void naoLocalizarComEmailInexistente(){

        Optional<Customer> customer1 = customers.ofEmail(new Email(UUID.randomUUID().toString() + "@gmail.com"));

        Assertions.assertThat(customer1).isNotPresent();

    }

    @Test
    public void deveRetornarSeEmailEmUso(){

        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        Assertions.assertThat(customers.isEmailUnique(customer.email(), customer.id())).isTrue();
        Assertions.assertThat(customers.isEmailUnique(customer.email(), new CustomerId())).isFalse();
        Assertions.assertThat(customers.isEmailUnique(new Email("teste@gmail.com"), new CustomerId())).isTrue();

    }

}
