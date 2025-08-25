package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;


@DataJpaTest
@Import({CustomerPersistenceProvider.class, CustomerPersistenceEntityAssembler.class, CustomerPersistenceEntityDisassembler.class})
class CustomerPersistenceProviderTest {

    @Autowired
    CustomerPersistenceProvider persistenceProvider;

    @Test
    void devePersistirCustomer(){
        Customer customer = CustomerTestDataBuilder.existingdCustomer().build();
        persistenceProvider.add(customer);

        Customer customer1 = persistenceProvider.ofId(customer.id()).orElseThrow();

        Assertions.assertThat(customer1.id()).isNotNull();
        Assertions.assertThat(customer1.address().zipCode().value()).isEqualTo("12345");
        Assertions.assertThat(persistenceProvider.count()).isEqualTo(1);


    }

}