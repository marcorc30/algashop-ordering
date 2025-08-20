package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;


@NoArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class CustomerPersistenceEntity {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String document;
    private Boolean promotionNotificationsAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private Integer loyaltPoints;
    private AddressEmbeddable address;


    @Builder
    public CustomerPersistenceEntity(UUID id, String firstName, String lastName, LocalDate birthDate,
                                     String email, String phone, String document, Boolean promotionNotificationsAllowed,
                                     Boolean archived, OffsetDateTime registeredAt, OffsetDateTime archivedAt,
                                     Integer loyaltPoints, AddressEmbeddable address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.document = document;
        this.promotionNotificationsAllowed = promotionNotificationsAllowed;
        this.archived = archived;
        this.registeredAt = registeredAt;
        this.archivedAt = archivedAt;
        this.loyaltPoints = loyaltPoints;
        this.address = address;
    }
}
