package com.algaworks.algashop.ordering.domain.model.commons;



import com.algaworks.algashop.ordering.domain.model.customer.BirthDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class BirthDateTest {

    @Test
    void quando_criar_data_correta_entao_retorne_data(){
        BirthDate date = new BirthDate(LocalDate.of(2020, 1, 1));

        Assertions.assertThat(date.value()).isEqualTo(LocalDate.of(2020,1,1));
    }

    @Test
    void quando_passar_data_nascimento_em_abril_2020_deve_retornar_5_anos(){
        BirthDate date = new BirthDate(LocalDate.of(2020,4,20));

        Assertions.assertThat(date.age()).isEqualTo(5);
    }

    @Test
    void quando_passar_data_futura_entao_lancar_excecao(){

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new BirthDate(LocalDate.of(2026, 1, 1)));


    }

    @Test
    void quando_passar_data_igual_nula_entao_lancar_excecao(){

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new BirthDate(null));
    }
}