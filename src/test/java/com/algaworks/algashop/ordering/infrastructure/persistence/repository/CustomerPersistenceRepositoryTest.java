package com.algaworks.algashop.ordering.infrastructure.persistence.repository;


import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class CustomerPersistenceRepositoryTest {

    @Autowired
    CustomerPersistenceEntityRepository repository;

    @Test
    void shouldPersist(){

        CustomerPersistenceEntity persistenceEntity = CustomerPersistenceEntityTestDataBuilder.exiting().build();
        CustomerPersistenceEntity persistenceEntityGravado = repository.saveAndFlush(persistenceEntity);

        Assertions.assertThat(persistenceEntityGravado).isNotNull();

        Optional<CustomerPersistenceEntity> persistenceCarregado = repository.findById(persistenceEntityGravado.getId());

        Assertions.assertThat(persistenceCarregado.isEmpty()).isEqualTo(false);
        Assertions.assertThat(persistenceCarregado.orElseThrow().getDocument()).isEqualTo("564464");


    }
}
