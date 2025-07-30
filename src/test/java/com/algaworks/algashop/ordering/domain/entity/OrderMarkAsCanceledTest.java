package com.algaworks.algashop.ordering.domain.entity;


import com.algaworks.algashop.ordering.domain.exception.OrderCannotBeCanceledException;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderMarkAsCanceledTest {

    @Test
    void dado_um_pedido_diferente_de_cancelado_quando_tentar_cancelar_entao_mudar_status_para_cancelado(){
        Order order = Order.draft(new CustomerId());

        order.cancel();

        Assertions.assertThat(order.isCanceled()).isTrue();
        Assertions.assertThat(order.status()).isEqualTo(OrderStatus.CANCELED);
    }

    @Test
    void dado_um_pedido_cancelado_quando_tentar_cancelar_novamente_lancar_excecao(){
        Order order = Order.draft(new CustomerId());

        order.cancel();

        Assertions.assertThatExceptionOfType(OrderCannotBeCanceledException.class)
                .isThrownBy(() -> order.cancel());
    }

}
