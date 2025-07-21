package com.algaworks.algashop.ordering.domain.valueobject;


import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerIdTest {

    @Test
    void quando_criar_customerId_nulo_lancar_excecao(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new CustomerId(null));
    }

}