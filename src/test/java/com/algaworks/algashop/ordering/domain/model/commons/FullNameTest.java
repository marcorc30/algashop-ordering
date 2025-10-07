package com.algaworks.algashop.ordering.domain.model.commons;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class FullNameTest {

    @Test
    void quando_criar_nome_completo_com_valores_valido_entao_retorne_nome_completo(){

        FullName fullName = new FullName("marco", "cavalheiro");

//        Assertions.assertThat(fullName.firstName()).isEqualTo("marco");
//        Assertions.assertThat(fullName.lastName()).isEqualTo("cavalheiro");

        Assertions.assertWith(fullName,
                f -> Assertions.assertThat(fullName.firstName()).isEqualTo("marco"),
                f -> Assertions.assertThat(fullName.lastName()).isEqualTo("cavalheiro")
        );

    }

    @Test
    void quando_criar_nome_ou_sobrenome_null_ou_branco_entao_lancar_exception(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new FullName(null, "cavalheiro"));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new FullName("marco", ""));
    }

}