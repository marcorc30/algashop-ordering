package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCartItem;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity

@EntityListeners(AuditingEntityListener.class)
public class ShoppingCartPersistenceEntity {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private UUID customerId;
    private BigDecimal totalAmount;
    private Integer totalItens;
    private OffsetDateTime createdAt;

    @JoinColumn
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ShoppingCartItemPersistenceEntity> items;

    @CreatedBy
    private UUID createByUserId;

    @CreatedDate
    private OffsetDateTime createAt;

    @LastModifiedDate
    private OffsetDateTime lastModifiedAt;

    @LastModifiedBy
    private UUID lastModifiedByUserId;

    @Version
    private Long version;


    @Builder
    public ShoppingCartPersistenceEntity(UUID id, UUID customerId, BigDecimal totalAmount, Integer totalItens,
                                         OffsetDateTime createdAt, Set<ShoppingCartItemPersistenceEntity> items) {
        this.id = id;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.totalItens = totalItens;
        this.createdAt = createdAt;
        this.items = items;
    }
}
