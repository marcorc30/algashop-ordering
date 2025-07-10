package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.utility.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

class CustomerTest {

    @Test
    void dado_email_invalido_quando_criar_deve_lancar_excecao() {

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    new Customer(
                            IdGenerator.generateTimeBaseUUID(),
                            "marco",
                            LocalDate.of(1991, 7, 3),
                            "invalid",
                            "55666656",
                            "65665466545",
                            false,
                            OffsetDateTime.now()

                    );
                });
    }

    @Test
    void dado_email_invalido_quando_alterar_deve_lancar_excecao(){
        Customer customer = new Customer(
                IdGenerator.generateTimeBaseUUID(),
                "marco",
                LocalDate.of(1991, 7, 3),
                "marco@gmail.com",
                "55666656",
                "65665466545",
                false,
                OffsetDateTime.now());



        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    customer.changeEmail("invalid");
                });

//        customer.changeEmail("invalid");
    }

    @Test
    void dado_cliente_nao_arquivado_quando_arquivar_deve_anonimizar(){
        Customer customer = new Customer(
                IdGenerator.generateTimeBaseUUID(),
                "marco",
                LocalDate.of(1991, 7, 3),
                "marco@gmail.com",
                "55666656",
                "65665466545",
                false,
                OffsetDateTime.now());

        customer.archive();

        Assertions.assertWith(customer,
                c -> Assertions.assertThat(c.fullName()).isEqualTo("Anonymous"),
                c -> Assertions.assertThat(c.email()).isNotEqualTo("marco@gmail.com"),
                c -> Assertions.assertThat(c.phone()).isEqualTo("000-000-0000"),
                c -> Assertions.assertThat(c.document()).isEqualTo("000-00-0000"),
                c -> Assertions.assertThat(c.birthDate()).isNull()

                );
    }

    @Test
    void dado_cliente_arquivado_entao_nao_pode_arquivar_e_nem_alterar(){
        Customer customer = new Customer(
                IdGenerator.generateTimeBaseUUID(),
                "marco",
                LocalDate.of(1991, 7, 3),
                "marco@gmail.com",
                "55666656",
                "65665466545",
                false,
                OffsetDateTime.now());

        customer.archive();

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> {
                    customer.archive();
                });

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> {
                    customer.changeEmail("marcorc30@gmail.com");
                });

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> {
                    customer.changeName("marcos");
                });

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> {
                    customer.changePhone("5464666546");
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
        Customer customer = new Customer(
                IdGenerator.generateTimeBaseUUID(),
                "marco",
                LocalDate.of(1991, 7, 3),
                "marco@gmail.com",
                "55666656",
                "65665466545",
                false,
                OffsetDateTime.now());

        customer.archive();

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                        .isThrownBy(() -> {
                           customer.changeEmail("marcorc30@gmail.com");
                        });


    }
}