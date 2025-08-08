package com.algaworks.algashop.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class BillingInfoTest {

    @Test
    void quando_criar_billing_info_entao_se_conter_valor_null_lancar_excecao(){

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
    void quando_criar_billing_info_entao_retornar_valores_passados(){
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

                .recipient(Recipient.builder()
                        .phone(new Phone("5544555"))
                        .document(new Document("555455"))
                        .fullName(new FullName("teste", "teste"))
                        .build())
                .cost(Money.ZERO)
                .expectedDate(LocalDate.now())
                .build();

        Assertions.assertThat(build.recipient().phone().toString()).isEqualTo("5544555");
    }


}