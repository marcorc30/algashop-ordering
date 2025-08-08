package com.algaworks.algashop.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ShippingInfoTest {

    @Test
    void quando_criar_shipping_info_entao_se_conter_valor_null_lancar_excecao(){

        Assertions.assertThatNullPointerException()
                .isThrownBy(() -> Shipping.builder()
                        .address(Address.builder()
                                .number("54545")
                                .state("teste")
                                .city("teste")
                                .complement("sfdsfsdfs")
                                .street("sdfsdfs")
                                .zipCode(new ZipCode("12345"))
                                .build())
                        .recipient(Recipient.builder()
                                .phone(null)
                                .document(new Document("555455"))
                                .fullName(new FullName("teste","teste"))
                                .build())
                        .build());
    }

    @Test
    void quando_criar_shipping_info_entao_retornar_valores_passados(){
        Shipping build = Shipping.builder()
                .address(Address.builder()
                        .number("54545")
                        .state("teste")
                        .city("teste")
                        .complement("sfdsfsdfs")
                        .street("sdfsdfs")
                        .zipCode(new ZipCode("12345"))
                        .neighborhood("65465466")
                        .build())
                .cost(new Money("10.0"))
                .expectedDate(LocalDate.now())
                .address(Address.builder()
                        .number("54545")
                        .state("teste")
                        .city("teste")
                        .complement("sfdsfsdfs")
                        .street("sdfsdfs")
                        .zipCode(new ZipCode("12345"))
                        .neighborhood("teste")
                        .build())
                .recipient(Recipient.builder()
                        .phone(null)
                        .document(new Document("555455"))
                        .fullName(new FullName("teste","teste"))
                        .phone(new Phone("5544555"))
                        .build())
                .build();

        Assertions.assertThat(build.recipient().phone().toString()).isEqualTo("5544555");
    }

}