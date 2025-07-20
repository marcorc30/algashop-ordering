package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class EmailTest {

    @Test
    void quando_criar_email_entao_retorna_email_criado(){
        Email email  = new Email("marco@email.com");

        Assertions.assertThat(email.value()).isEqualTo("marco@email.com");
    }

    @Test
    void quando_passar_email_em_branco_entao_retorne_excecao(){

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Email(""));
    }

    @Test
    void quando_passar_email_nulo_entao_retorne_excecao(){

        Assertions.assertThatNullPointerException()
                .isThrownBy(() -> new Email(null));

    }

    @Test
    void quando_passar_email_invalido_entao_retorne_excecao(){

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Email("invalid"));

    }
  
}