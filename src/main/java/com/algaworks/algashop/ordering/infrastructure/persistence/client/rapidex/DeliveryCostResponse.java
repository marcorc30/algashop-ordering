package com.algaworks.algashop.ordering.infrastructure.persistence.client.rapidex;

/*
DTO com as informacoes para a api externa do rapidex
Campos definidos pela api externa (campos de saida)
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryCostResponse {

    private String deliveryCost;
    private Long estimatedDaysToDeliver;

}
