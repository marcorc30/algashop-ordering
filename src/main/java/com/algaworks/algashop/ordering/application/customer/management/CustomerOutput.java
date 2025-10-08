package com.algaworks.algashop.ordering.application.customer.management;

import com.algaworks.algashop.ordering.application.commons.AddressData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOutput {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String document;
    private LocalDate birthDate;
    private Integer loyaltPoints;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private boolean promotionNotificationsAllowed;
    private Boolean archived;
    private AddressData address;

}
