package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.OrderInvalidShippingDeliveryDateException;
import com.algaworks.algashop.ordering.domain.exception.OrderStatusCannotBeChangeException;
import com.algaworks.algashop.ordering.domain.utility.IdGenerator;
import com.algaworks.algashop.ordering.domain.valueobject.*;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


class OrderTest {

    @Test
    void testOrder(){
        Order.existing()
                .id(new OrderId())
                .expectedDeliveryDate(null)
                .placedAt(null)
                .readyAt(null)
                .canceledAt(null)
                .shippingCost(null)
                .billing(null)
                .customerId(new CustomerId())
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .items(new HashSet<>())
                .paidAt(null)
                .totalAmount(Money.ZERO)
                .quantity(Quantity.ZER0)
                .shipping(null)
                .status(OrderStatus.DRAFT)
                .build();
    }

    @Test
    void testNewOrder(){
        Order draft = Order.draft(new CustomerId());

        Assertions.assertThat(draft.status()).isEqualTo(OrderStatus.DRAFT);


    }

    @Test
    void adicionarItemPedido(){
        Order order = Order.draft(new CustomerId());

        order.addItem(
                new ProductId(),
                new ProductName("produto1"),
                new Money("10.0"),
                new Quantity(10));

        Assertions.assertThat(order.items()).isNotEmpty();

        OrderItem orderItem = order.items().iterator().next();

        Assertions.assertWith(orderItem,
                (i) -> Assertions.assertThat(orderItem.productName()).isEqualTo(new ProductName("produto1")),
                (i) -> Assertions.assertThat(orderItem.quantity()).isEqualTo(new Quantity(10)),
                (i) -> Assertions.assertThat(orderItem.orderId()).isNotNull()
        );
    }

    @Test
    void quando_tentar_alterar_set_items_entao_lancar_excecao(){
        Order order = Order.draft(new CustomerId());

        order.addItem(
                new ProductId(),
                new ProductName("produto1"),
                new Money("10.0"),
                new Quantity(10));

        Set<OrderItem> items = order.items();

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
                        .isThrownBy(() -> items.clear());


    }

    @Test
    void quando_tentar_alterar_para_status_invalido_do_pedido_lancar_excecao(){
        Order order = Order.draft(new CustomerId());
        order.place();
        Assertions.assertThat(order.isPlaced()).isTrue();
    }

    @Test
    void dado_um_pedido_gerado_quando_tentar_atribuir_place_novamente_gerar_excecao(){
        Order order = Order.draft(new CustomerId());
        order.place();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangeException.class)
                .isThrownBy(() -> order.place());
    }

    @Test
    void dado_um_pedido_rascunho_alterar_informacoes_entrega(){
        Order order = Order.draft(new CustomerId());

        Address address = Address.builder()
                .neighborhood("teste")
                .street("teste")
                .city("teste")
                .complement("teste")
                .zipCode(new ZipCode("55555"))
                .number("5454545")
                .state("teste")
                .build();

        ShippingInfo shippingInfo = ShippingInfo.builder()
                .address(address)
                .phone(new Phone("555-555-555"))
                .fullName(new FullName("fulano", "de tal"))
                .document(new Document("554555"))
                .build();

        Money shippingCusto = Money.ZERO;
        LocalDate date = LocalDate.now().plusDays(2);

        order.changeShipping(shippingInfo, shippingCusto, date);

        Assertions.assertThat(order.shipping().phone()).isEqualTo(new Phone("555-555-555"));
        Assertions.assertThat(order.expectedDeliveryDate()).isEqualTo(LocalDate.now().plusDays(2));

    }


    @Test
    void dado_um_pedido_alterar_dados_entrega_com_data_passada_entao_gerar_excecao(){
        Order order = Order.draft(new CustomerId());

        Address address = Address.builder()
                .neighborhood("teste")
                .street("teste")
                .city("teste")
                .complement("teste")
                .zipCode(new ZipCode("55555"))
                .number("5454545")
                .state("teste")
                .build();

        ShippingInfo shippingInfo = ShippingInfo.builder()
                .address(address)
                .phone(new Phone("555-555-555"))
                .fullName(new FullName("fulano", "de tal"))
                .document(new Document("554555"))
                .build();

        Money shippingCusto = Money.ZERO;
        LocalDate date = LocalDate.now().minusDays(2);

        Assertions.assertThatExceptionOfType(OrderInvalidShippingDeliveryDateException.class)
                        .isThrownBy(() -> order.changeShipping(shippingInfo, shippingCusto, date));


    }

}