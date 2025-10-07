package com.algaworks.algashop.ordering.domain.model.commons;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PhoneTest {

    @Test
    void quando_criar_phone_em_branco_ou_nulo_entao_lancar_excecao(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                   new Phone("");
                });

        Assertions.assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> new Phone(null));

    }

    @Test
    void quando_criar_phone_valido_entao_retornar_phone() {
        Phone phone = new Phone("61 999129929");

        Assertions.assertThat(phone.value()).isEqualTo("61 999129929");

    }


}