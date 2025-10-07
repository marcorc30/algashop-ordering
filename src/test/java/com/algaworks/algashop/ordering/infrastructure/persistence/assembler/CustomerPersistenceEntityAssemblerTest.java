package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class CustomerPersistenceEntityAssemblerTest {

    @Test
    void dado_um_domain_converter_para_entity(){

        CustomerPersistenceEntityAssembler assembler = new CustomerPersistenceEntityAssembler();

        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        CustomerPersistenceEntity customerPersistenceEntity = assembler.fromDomain(customer);

        Assertions.assertWith(customerPersistenceEntity,
                c -> Assertions.assertThat(c.getAddress()).isNotNull(),
                c -> Assertions.assertThat(c.getBirthDate()).isNotNull(),
                c -> Assertions.assertThat(c.getAddress()).isNotNull(),
                c -> Assertions.assertThat(c.getFirstName()).isEqualTo(customer.fullName().firstName()),
                c -> Assertions.assertThat(c.getAddress().getCity()).isEqualTo(customer.address().city())
                );


    }

}