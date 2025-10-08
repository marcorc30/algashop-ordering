package com.algaworks.algashop.ordering.infrastructure.persistence.disassembler;

import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class CustomerPersistenceEntityDisassemblerTest {

    CustomerPersistenceEntityDisassembler disassembler = new CustomerPersistenceEntityDisassembler();

    @Test
    void dado_um_entity_converter_para_domain(){

        CustomerPersistenceEntity persistenceEntity = CustomerPersistenceEntityTestDataBuilder.exiting().build();
        Customer domainEntity = disassembler.toDomainEntity(persistenceEntity);

        Assertions.assertWith(domainEntity,
                d -> Assertions.assertThat(domainEntity.birthDate()).isNotNull(),
                d -> Assertions.assertThat(domainEntity.birthDate().value()).isEqualTo(LocalDate.now().minusYears(20)),
                d -> Assertions.assertThat(domainEntity.document().value()).isEqualTo("564464")
                );









    }

}