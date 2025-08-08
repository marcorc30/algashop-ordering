package com.algaworks.algashop.ordering.infrastructure.persistence.repository;

import com.algaworks.algashop.ordering.domain.model.utility.IdGenerator;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@Transactional

//testa os componentes de peristencia de forma isolada, não carregando todo o contexto do ambiente
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderPersistenceEntityRepositoryTest {

//    @Autowired
    private final OrderPersistenceEntityRepository orderPersistenceEntityRepository;

    @Autowired
    public OrderPersistenceEntityRepositoryTest(OrderPersistenceEntityRepository orderPersistenceEntityRepository) {
        this.orderPersistenceEntityRepository = orderPersistenceEntityRepository;
    }

    @Test
    public void shouldPersist(){

        OrderPersistenceEntity orderPersistence = OrderPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .customerId(IdGenerator.generateTimeBaseUUID())
                .totalItems(2)
                .totalAmount(new BigDecimal(1000))
                .status("DRAFT")
                .paymentMethod("CREDIT_CARD")
                .placedAt(OffsetDateTime.now())
                .build();

        orderPersistenceEntityRepository.saveAndFlush(orderPersistence);

        Assertions.assertThat(orderPersistenceEntityRepository.existsById(orderPersistence.getId())).isTrue();

    }

    @Test
    public void shouldCount(){
        long count = orderPersistenceEntityRepository.count();

        Assertions.assertThat(count).isZero();
    }

    @Test
    public void shouldFind(){

        long id = IdGenerator.generateTSID().toLong();

        OrderPersistenceEntity orderPersistence = OrderPersistenceEntity.builder()
                .id(id)
                .customerId(IdGenerator.generateTimeBaseUUID())
                .totalItems(2)
                .totalAmount(new BigDecimal(1000))
                .status("DRAFT")
                .paymentMethod("CREDIT_CARD")
                .placedAt(OffsetDateTime.now())
                .build();

        orderPersistenceEntityRepository.saveAndFlush(orderPersistence);

        Optional<OrderPersistenceEntity> orderOptional = orderPersistenceEntityRepository.findById(id);

        Assertions.assertThat(orderOptional).isPresent();


    }


}