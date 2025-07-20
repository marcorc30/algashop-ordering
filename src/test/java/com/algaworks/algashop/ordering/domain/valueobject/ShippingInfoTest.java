package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShippingInfoTest {

    @Test
    void quando_criar_shipping_info_entao_se_conter_valor_null_lancar_excecao(){

        Assertions.assertThatNullPointerException()
                .isThrownBy(() -> ShippingInfo.builder()
                        .address(Address.builder()
                                .number("54545")
                                .state("teste")
                                .city("teste")
                                .complement("sfdsfsdfs")
                                .street("sdfsdfs")
                                .zipCode(new ZipCode("12345"))
                                .build())
                        .phone(null)
                        .document(new Document("555455"))
                        .fullName(new FullName("teste","teste"))
                        .build());
    }

    @Test
    void quando_criar_shipping_info_entao_retornar_valores_passados(){
        ShippingInfo build = ShippingInfo.builder()
                .address(Address.builder()
                        .number("54545")
                        .state("teste")
                        .city("teste")
                        .complement("sfdsfsdfs")
                        .street("sdfsdfs")
                        .zipCode(new ZipCode("12345"))
                        .neighborhood("65465466")
                        .build())
                .phone(new Phone("5544555"))
                .document(new Document("555455"))
                .fullName(new FullName("teste", "teste"))
                .build();

        Assertions.assertThat(build.phone().toString()).isEqualTo("5544555");
    }

}