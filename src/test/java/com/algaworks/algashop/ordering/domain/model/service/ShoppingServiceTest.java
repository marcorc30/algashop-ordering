package com.algaworks.algashop.ordering.domain.model.service;

import com.algaworks.algashop.ordering.domain.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCartTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.repository.ShoppingCarts;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ShoppingServiceTest {

    @Mock
    ShoppingCarts shoppingCarts;

    @Mock
    Customers customers;

    @InjectMocks
    ShoppingService shoppingService;

    @Test
    public void compraBemSucedida(){

        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

        Mockito.when(customers.exists(Mockito.any(CustomerId.class))).thenReturn(true);
        Mockito.when(shoppingCarts.ofCustomer(Mockito.any(CustomerId.class))).thenReturn(Optional.of(shoppingCart));

        shoppingService.startShopping(customer.id());

    }

    @Test
    public void clienteNaoEncontrado(){

        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

        Mockito.when(customers.exists(Mockito.any(CustomerId.class))).thenReturn(false);

        Assertions.assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> shoppingService.startShopping(customer.id()));

        Mockito.verify(shoppingCarts, Mockito.never()).ofCustomer(Mockito.any());
    }


}