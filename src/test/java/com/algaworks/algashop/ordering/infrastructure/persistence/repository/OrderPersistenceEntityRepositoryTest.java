package com.algaworks.algashop.ordering.infrastructure.persistence.repository;

import com.algaworks.algashop.ordering.domain.model.IdGenerator;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.SpringDataAuditingConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.OrderPersistenceEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

//@SpringBootTest
//@Transactional

//testa os componentes de peristencia de forma isolada, não carregando todo o contexto do ambiente
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(SpringDataAuditingConfig.class)
class OrderPersistenceEntityRepositoryTest {

//    @Autowired
    private final OrderPersistenceEntityRepository orderPersistenceEntityRepository;
    private final CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

    private CustomerPersistenceEntity customerPersistenceEntity;


    @Autowired
    public OrderPersistenceEntityRepositoryTest(OrderPersistenceEntityRepository orderPersistenceEntityRepository, CustomerPersistenceEntityRepository customerPersistenceEntityRepository) {
        this.orderPersistenceEntityRepository = orderPersistenceEntityRepository;
        this.customerPersistenceEntityRepository = customerPersistenceEntityRepository;
    }

    @BeforeEach
    public void setup(){
        UUID customerId = DEFAULT_CUSTOMER_ID.value();

        if (!customerPersistenceEntityRepository.existsById(customerId)){
            customerPersistenceEntity =  customerPersistenceEntityRepository.saveAndFlush(CustomerPersistenceEntityTestDataBuilder.exiting().build());

        }


    }

    @Test
    public void shouldPersist(){

        OrderPersistenceEntity orderPersistence = OrderPersistenceEntityTestDataBuilder.existingOrder()
                .customer(customerPersistenceEntity)
                .build();

        OrderPersistenceEntity orderPersistenceEntity = orderPersistenceEntityRepository.saveAndFlush(orderPersistence);

        int size = orderPersistenceEntity.getItems().size();


        Assertions.assertThat(orderPersistenceEntityRepository.existsById(orderPersistence.getId())).isTrue();
        Assertions.assertThat(size).isEqualTo(2);

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
                .customer(customerPersistenceEntity)
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

    @Test
    public void shouldPersistAndSaveAudit(){
        OrderPersistenceEntity orderPersistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder()
                .customer(customerPersistenceEntity)
                .build();
        orderPersistenceEntity = orderPersistenceEntityRepository.saveAndFlush(orderPersistenceEntity);


        Assertions.assertThat(orderPersistenceEntity.getCreateByUserId()).isNotNull();
        Assertions.assertThat(orderPersistenceEntity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(orderPersistenceEntity.getLastModifiedByUserId()).isNotNull();


    }


}