package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.factory.OrderFactory;
import com.algaworks.algashop.ordering.domain.model.exception.OrderCannotBeEditedException;
import com.algaworks.algashop.ordering.domain.model.exception.OrderInvalidShippingDeliveryDateException;
import com.algaworks.algashop.ordering.domain.model.exception.OrderStatusCannotBeChangeException;
import com.algaworks.algashop.ordering.domain.model.exception.ProductOutOfStockException;
import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


class OrderTest {

//    @Test
    void testOrder(){

        Address address = Address.builder()
                .state("teste")
                .street("teste")
                .number("5455")
                .neighborhood("dsfdsfsd")
                .zipCode(new ZipCode("55555"))
                .complement("teste")
                .build();

        Recipient recipient = Recipient.builder()
                .document(new Document("54545"))
                .fullName(new FullName("teste", "teste"))
                .phone(new Phone("555-555-555"))
                .build();

        Shipping shipping = Shipping.builder()
                .address(address)
                .cost(new Money("10.0"))
                .expectedDate(LocalDate.now())
                .recipient(recipient)
                .build();

        Order.existing()
                .id(new OrderId())
                .placedAt(null)
                .readyAt(null)
                .canceledAt(null)
                .billing(null)
                .customerId(new CustomerId())
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .items(new HashSet<>())
                .paidAt(null)
                .totalAmount(Money.ZERO)
                .quantity(Quantity.ZER0)
                .shipping(shipping)
                .status(OrderStatus.DRAFT)
                .build();
    }

    @Test
    void testNewOrder(){
//        Order draft = Order.draft(new CustomerId());
        Order draft = Order.draft(new CustomerId());

        Assertions.assertThat(draft.status()).isEqualTo(OrderStatus.DRAFT);


    }

    @Test
    void adicionarItemPedido(){
        Order order = Order.draft(new CustomerId());
        Product product = ProductTestDataBuilder.aProduct().build();

        order.addItem(
                product,
                new Quantity(10));

        Assertions.assertThat(order.items()).isNotEmpty();

        OrderItem orderItem = order.items().iterator().next();

        Assertions.assertWith(orderItem,
                (i) -> Assertions.assertThat(orderItem.productName()).isEqualTo(new ProductName("produto teste")),
                (i) -> Assertions.assertThat(orderItem.quantity()).isEqualTo(new Quantity(10)),
                (i) -> Assertions.assertThat(orderItem.orderId()).isNotNull()
        );
    }

    @Test
    void quando_tentar_alterar_set_items_entao_lancar_excecao(){
        Order order = Order.draft(new CustomerId());
        Product product = ProductTestDataBuilder.aProduct().build();

        order.addItem(
                product,
                new Quantity(10));

        Set<OrderItem> items = order.items();

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
                        .isThrownBy(() -> items.clear());


    }

//    @Test
//    void quando_tentar_alterar_para_status_invalido_do_pedido_lancar_excecao(){
//        Order order = Order.draft(new CustomerId());
//        order.place();
//        Assertions.assertThat(order.isPlaced()).isTrue();
//    }

    @Test
    void dado_um_pedido_gerado_quando_tentar_atribuir_place_novamente_gerar_excecao(){
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

        Shipping shippingInfo = Shipping.builder()
                .address(address)
                .recipient(Recipient.builder()
                        .phone(new Phone("555-555-555"))
                        .fullName(new FullName("fulano", "de tal"))
                        .document(new Document("554555"))
                        .build())
                .cost(new Money("10.0"))
                .expectedDate(LocalDate.now())
                .build();

        Money shippingCusto = Money.ZERO;
        LocalDate date = LocalDate.now().plusDays(2);

        FullName fullName = new FullName("marco", "rodrigues");
        Document document = new Document("554555");
        Phone phone = new Phone("555-555-555");
        Email email = new Email("marco@email.com");

        Billing billingInfo = new Billing(fullName,document,phone, email, address);



        order.changeShipping(shippingInfo);
        order.changeBilling(billingInfo);
        order.changePaymentMethod(PaymentMethod.CREDIT_CARD);

        order.addItem(ProductTestDataBuilder.aProduct().build(), new Quantity(10));

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

        Shipping shippingInfo = Shipping.builder()
                .address(address)
                .recipient(Recipient.builder()
                        .phone(new Phone("555-555-555"))
                        .fullName(new FullName("fulano", "de tal"))
                        .document(new Document("554555"))
                        .build())
                .cost(new Money("10.0"))
                .expectedDate(LocalDate.now())
                .build();

        Money shippingCusto = Money.ZERO;
        LocalDate date = LocalDate.now().plusDays(2);

        Shipping.ShippingBuilder shippingBuilder = shippingInfo.toBuilder().expectedDate(date);

        order.changeShipping(shippingBuilder.build());

        Assertions.assertThat(order.shipping().recipient().phone()).isEqualTo(new Phone("555-555-555"));
        Assertions.assertThat(order.shipping().expectedDate()).isEqualTo(LocalDate.now().plusDays(2));

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

        Shipping shippingInfo = Shipping.builder()
                .address(address)
                .recipient(Recipient.builder()
                        .phone(new Phone("555-555-555"))
                        .fullName(new FullName("fulano", "de tal"))
                        .document(new Document("554555"))
                        .build())
                .cost(new Money("10.0"))
                .expectedDate(LocalDate.now())
                .build();

        Money shippingCusto = Money.ZERO;
        LocalDate date = LocalDate.now().minusDays(2);
        Shipping.ShippingBuilder shippingBuilder = shippingInfo.toBuilder().expectedDate(date);

        Assertions.assertThatExceptionOfType(OrderInvalidShippingDeliveryDateException.class)
                        .isThrownBy(() -> order.changeShipping(shippingBuilder.build()));


    }

    @Test
    void dado_um_pedido_com_items_entao_alterar_quantidade_de_um_item(){
        Order order = Order.draft(new CustomerId());
        Product product = ProductTestDataBuilder.aProduct().build();

        order.addItem(
                product,
                new Quantity(10)
        );

//        OrderItem item = order.items().stream().filter(i -> i.quantity().equals(new Quantity(10))).findFirst().get();

        OrderItem item = order.items().iterator().next();
        order.changeItemQuantity(item.id(), new Quantity(20));

        Assertions.assertThat(item.quantity()).isEqualTo(new Quantity(20));
        Assertions.assertThat(order.totalAmount()).isEqualTo(new Money("200.00"));


    }

    @Test
    void dado_um_pedido_quando_incluir_item_sem_estoque_entao_lancar_excecao(){
        Order order = Order.draft(new CustomerId());
        Product product = ProductTestDataBuilder.aProductUnavailable().build();

        Assertions.assertThatExceptionOfType(ProductOutOfStockException.class)
                        .isThrownBy(() -> {
                            order.addItem(
                                    product,
                                    new Quantity(10)
                            );
                        });


    }

    @Test
    void dado_um_pedido_rascunho_quando_editar_entao_permitir_edicao(){
        Address address = Address.builder()
                .complement("teste")
                .state("teste")
                .city("teste")
                .zipCode(new ZipCode("55555"))
                .number("5555")
                .street("teste")
                .neighborhood("teste")
                .build();

        Billing billing = Billing.builder()
                .email(new Email("marco@email.com"))
                .address(address)
                .phone(new Phone("555-555-555"))
                .document(new Document("4455414"))
                .fullName(new FullName("teste", "teste"))
                .build();

        Recipient recipient = Recipient.builder()
                .phone(new Phone("555-555-555"))
                .fullName(new FullName("teste", "teste"))
                .document(new Document("55555"))
                .build();


        Shipping shipping = Shipping.builder()
                .expectedDate(LocalDate.now())
                .cost(new Money("10.0"))
                .address(address)
                .recipient(recipient)
                .build();

        Product product = Product.builder()
                .id(new ProductId())
                .inStock(true)
                .name(new ProductName("teste"))
                .price(new Money("10.0"))
                .build();



        Quantity quantity = Quantity.ZER0;

        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;

        Order order = OrderFactory.filled(new CustomerId(), shipping, billing, paymentMethod, product, quantity);
        order.addItem(product, quantity);

        order.place();

        Assertions.assertThat(order.isPlaced()).isTrue();

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class)
                .isThrownBy(() -> order.changePaymentMethod(PaymentMethod.GATEWAY_BALANCE));


    }

    @Test
    void dado_um_pedido_fechado_quando_excluir_entao_lancar_excecao(){
        Address address = Address.builder()
                .complement("teste")
                .state("teste")
                .city("teste")
                .zipCode(new ZipCode("55555"))
                .number("5555")
                .street("teste")
                .neighborhood("teste")
                .build();

        Billing billing = Billing.builder()
                .email(new Email("marco@email.com"))
                .address(address)
                .phone(new Phone("555-555-555"))
                .document(new Document("4455414"))
                .fullName(new FullName("teste", "teste"))
                .build();

        Recipient recipient = Recipient.builder()
                .phone(new Phone("555-555-555"))
                .fullName(new FullName("teste", "teste"))
                .document(new Document("55555"))
                .build();


        Shipping shipping = Shipping.builder()
                .expectedDate(LocalDate.now())
                .cost(new Money("10.0"))
                .address(address)
                .recipient(recipient)
                .build();

        Product product = Product.builder()
                .id(new ProductId())
                .inStock(true)
                .name(new ProductName("teste"))
                .price(new Money("10.0"))
                .build();



        Quantity quantity = Quantity.ZER0;

        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;

        Order order = OrderFactory.filled(new CustomerId(), shipping, billing, paymentMethod, product, quantity);

        order.place();

        OrderItem orderItem = order.items().stream().iterator().next();

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class)
                .isThrownBy(() -> order.removeItem(orderItem.id()));


    }



}