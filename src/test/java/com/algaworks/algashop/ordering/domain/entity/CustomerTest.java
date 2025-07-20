package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.utility.IdGenerator;
import com.algaworks.algashop.ordering.domain.valueobject.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

class CustomerTest {


    @Test
    void dado_email_invalido_quando_criar_deve_lancar_excecao() {

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> CustomerTestDataBuilder.brandNewCustomer().email(new Email("invalid")).build());
    }

    @Test
    void dado_email_valido_quando_alterar_deve_lancar_excecao(){
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    customer.changeEmail(new Email("invalid"));
                });

    }

    @Test
    void dado_cliente_nao_arquivado_quando_arquivar_deve_anonimizar(){
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

        customer.archive();

        Assertions.assertWith(customer,
                c -> Assertions.assertThat(c.fullName()).isEqualTo(new FullName("anonymous","anonymous")),
                c -> Assertions.assertThat(c.email()).isNotEqualTo("marco@gmail.com"),
                c -> Assertions.assertThat(c.phone()).isEqualTo(new Phone("000-000-0000")),
                c -> Assertions.assertThat(c.document()).isEqualTo(new Document("000-00-0000")),
                c -> Assertions.assertThat(c.birthDate()).isNull(),
                c -> Assertions.assertThat(c.isPromotionNotificationsAllowed()).isFalse(),
                c -> Assertions.assertThat(c.address()).isEqualTo(
                        Address.builder()
                                .street("teste")
                                .number("Anonymized")
                                .neighborhood("teste")
                                .city("teste")
                                .state("teste")
                                .zipCode(new ZipCode("12345"))
                                .complement("SEM COMPLEMENTO")
                                .build())

                );
    }

    @Test
    void dado_cliente_arquivado_entao_nao_pode_arquivar_e_nem_alterar(){
        Customer customer = CustomerTestDataBuilder.existingAnonymyzedCustomer().build();


        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> {
                    customer.archive();
                });

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> {
                    customer.changeName(new FullName("marcos", "rodrigues"));
                });

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> {
                    customer.changePhone(new Phone("5464666546"));
                });

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> {
                    customer.enablePromotionNotifications();
                });

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> {
                    customer.disablePromotionNotifications();
                });
    }

    @Test
    void dado_cliente_arquivado_entao_nao_pode_alterar(){
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

        customer.archive();

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                        .isThrownBy(() -> {
                           customer.changeEmail(new Email("marcorc30@gmail.com"));
                        });


    }

    @Test
    void dado_credito_pontos_igual_ou_menor_que_zero_entao_lancar_excecao(){
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> {
                            customer.addLoyaltyPoints(new LoyaltPoints(0));
                        });

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    customer.addLoyaltyPoints(new LoyaltPoints(-10));
                });

    }

    @Test
    void dado_credito_pontos_maior_que_zero_entao_adicionar_ao_valor_existente(){
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

        customer.addLoyaltyPoints(new LoyaltPoints(10));
        customer.addLoyaltyPoints(new LoyaltPoints(20));

        Assertions.assertThat(customer.loyaltPoints()).isEqualTo(new LoyaltPoints(30));

    }
}