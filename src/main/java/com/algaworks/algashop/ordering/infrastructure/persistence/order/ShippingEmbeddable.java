package com.algaworks.algashop.ordering.infrastructure.persistence.order;

import com.algaworks.algashop.ordering.infrastructure.persistence.commons.AddressEmbeddable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class ShippingEmbeddable {

    private BigDecimal cost;
    private LocalDate expectedDate;
    private RecipientEmbedabble recipient;

    @Embedded
    private AddressEmbeddable address;
}
