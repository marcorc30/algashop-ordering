package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void quando_passar_valor_negativo_entao_lancar_excecao(){

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Money(new BigDecimal(-10.5)));

    }

//    @Test
//    void quando_passar_valor_nulo_entao_lancar_excecao(){
//
//        Assertions.assertThatNullPointerException()
//                .isThrownBy(() -> new Money(null));
//
//    }

    @Test
    void quando_passar_valor_string_entao_arredondar_corretamente(){

        Money money = new Money(new BigDecimal("10"));

        Assertions.assertThat(money.value()).isEqualTo(new BigDecimal(10.00).setScale(2, RoundingMode.HALF_EVEN));

    }

    @Test
    void quando_passar_valor_double_entao_arredondar_corretamente(){

        Money money = new Money(new BigDecimal(10));
        Assertions.assertThat(money.value()).isEqualTo(new BigDecimal(10.00).setScale(2, RoundingMode.HALF_EVEN));
    }


}