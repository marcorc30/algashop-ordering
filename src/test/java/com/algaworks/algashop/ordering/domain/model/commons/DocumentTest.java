package com.algaworks.algashop.ordering.domain.model.commons;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DocumentTest {

    @Test
    void quando_cadastrar_document_em_branco_ou_nulo_entao_lancar_exception(){

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Document(null));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Document(""));

    }

}