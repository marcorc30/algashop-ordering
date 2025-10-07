package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.commons.*;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartCantProceedToCheckoutException;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

@SpringBootTest
@Import(CustomerPersistenceEntityDisassembler.class)
class CheckoutServiceTest {

    @Autowired
    CheckoutService checkoutService;

    @Test
    public void validarCheckoutComItensDisponiveis(){


        Address address = Address.builder()
                .city("city")
                .state("state")
                .zipCode(new ZipCode("55555"))
                .complement("55555")
                .neighborhood("neighborhood")
                .number("66554")
                .complement("complement")
                .street("street")
                .build();

        Order order = Order.draft(DEFAULT_CUSTOMER_ID);
        Shipping shipping = Shipping.builder()
                .cost(new Money("20"))
                .expectedDate(LocalDate.now())
                .recipient(Recipient.builder()
                        .phone(new Phone("555-555-555"))
                        .document(new Document("55444"))
                        .fullName(new FullName("fulano", "de tal"))
                        .build())
                .address(address)
                .build();

        Billing billing = Billing.builder()
                .phone(new Phone("555-555-555"))
                .document(new Document("55444"))
                .email(new Email("fulano@gmail.com"))
                .address(address)
                .fullName(new FullName("filano", "de tal"))
                .build();

        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;

        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(true).build();

        Order checkout = checkoutService.checkout(shoppingCart, billing, shipping, paymentMethod);

        Assertions.assertThat(OrderStatus.PLACED).isEqualTo(checkout.status());


    }

    @Test
    public void validarCheckoutSemItensDisponiveis(){


        Address address = Address.builder()
                .city("city")
                .state("state")
                .zipCode(new ZipCode("55555"))
                .complement("55555")
                .neighborhood("neighborhood")
                .number("66554")
                .complement("complement")
                .street("street")
                .build();

        Order order = Order.draft(DEFAULT_CUSTOMER_ID);
        Shipping shipping = Shipping.builder()
                .cost(new Money("20"))
                .expectedDate(LocalDate.now())
                .recipient(Recipient.builder()
                        .phone(new Phone("555-555-555"))
                        .document(new Document("55444"))
                        .fullName(new FullName("fulano", "de tal"))
                        .build())
                .address(address)
                .build();

        Billing billing = Billing.builder()
                .phone(new Phone("555-555-555"))
                .document(new Document("55444"))
                .email(new Email("fulano@gmail.com"))
                .address(address)
                .fullName(new FullName("filano", "de tal"))
                .build();

        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;

        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();


        Assertions.assertThatExceptionOfType(ShoppingCartCantProceedToCheckoutException.class)
                .isThrownBy(() -> checkoutService.checkout(shoppingCart, billing, shipping, paymentMethod));


    }

    @Test
    public void validarCheckoutComCarrinhoVazio(){
        Address address = Address.builder()
                .city("city")
                .state("state")
                .zipCode(new ZipCode("55555"))
                .complement("55555")
                .neighborhood("neighborhood")
                .number("66554")
                .complement("complement")
                .street("street")
                .build();

        Order order = Order.draft(DEFAULT_CUSTOMER_ID);
        Shipping shipping = Shipping.builder()
                .cost(new Money("20"))
                .expectedDate(LocalDate.now())
                .recipient(Recipient.builder()
                        .phone(new Phone("555-555-555"))
                        .document(new Document("55444"))
                        .fullName(new FullName("fulano", "de tal"))
                        .build())
                .address(address)
                .build();

        Billing billing = Billing.builder()
                .phone(new Phone("555-555-555"))
                .document(new Document("55444"))
                .email(new Email("fulano@gmail.com"))
                .address(address)
                .fullName(new FullName("filano", "de tal"))
                .build();

        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;

        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();
        shoppingCart.empty();


        Assertions.assertThatExceptionOfType(ShoppingCartCantProceedToCheckoutException.class)
                .isThrownBy(() -> checkoutService.checkout(shoppingCart, billing, shipping, paymentMethod));

    }

}