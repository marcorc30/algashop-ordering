package com.algaworks.algashop.ordering.application.checkout;

import com.algaworks.algashop.ordering.domain.model.order.Billing;
import com.algaworks.algashop.ordering.domain.model.order.Shipping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyNowInput {

    //dados do envio
    private ShippingInput shipping;
    //dados de cobrança
    private BillingData billing;

    private UUID productId;
    private UUID customerId;
    private Integer quatity;
    private String paymentMethod;





}
