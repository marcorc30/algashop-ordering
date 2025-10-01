package com.algaworks.algashop.ordering.infrastructure.persistence.client.rapidex;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface RapiDexAPIClient {

    @PostExchange("/api/delivery-cost")
    DeliveryCostResponse calculate(@RequestBody DeliveryCostRequest request);
}
