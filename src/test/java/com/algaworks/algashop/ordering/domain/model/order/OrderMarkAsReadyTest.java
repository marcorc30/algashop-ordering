package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.order.OrderStatusCannotBeChangeException;
import com.algaworks.algashop.ordering.domain.model.order.Order;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
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
