package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.exception.OrderStatusCannotBeChangeException;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderMarkAsReadyTest {

    @Test
    void dado_um_pedido_draft_quando_pagar_entao_gerar_excecao(){
        Order order = Order.draft(new CustomerId());

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangeException.class)
                .isThrownBy(() -> order.markAsReady());

    }
}
