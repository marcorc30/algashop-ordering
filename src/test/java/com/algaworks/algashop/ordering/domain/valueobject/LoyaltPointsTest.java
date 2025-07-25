package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoyaltPointsTest {

    @Test
    void quando_criar_loyalpoint_menor_que_zero_gerar_excecao(){

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    new LoyaltPoints(-1);
                });

    }

    @Test
    void quando_adicionar_loyaltpoint_entao_incremente_valor(){

        LoyaltPoints l = new LoyaltPoints(10);
        LoyaltPoints l2 = l.add(5);

        Assertions.assertThat(l2.value()).isEqualTo(15);
    }
}