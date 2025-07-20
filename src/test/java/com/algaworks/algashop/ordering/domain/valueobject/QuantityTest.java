package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    @Test
    void quando_criar_quantity_com_valor_negativo_entao_valor_deve_lancar_excecao(){

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Quantity(-1));
    }

    @Test
    void quando_criar_entity_com_valor_nulo_entao_deve_lancar_nullpointerexception(){

        Assertions.assertThatNullPointerException()
                .isThrownBy(() -> new Quantity(null));
    }

    @Test
    void quando_somar_um_valor_ao_quantity_entao_deve_retornar_valor_somado(){
        Quantity q = new Quantity(5);

        Quantity q1 = q.add(new Quantity(10));

        Assertions.assertThat(q1.value()).isEqualTo(new Quantity(15).value());
    }

}