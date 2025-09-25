package com.algaworks.algashop.ordering.domain.model.service;

import com.algaworks.algashop.ordering.domain.exception.CustomerAlreadyHaveShoppingCartException;
import com.algaworks.algashop.ordering.domain.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.repository.ShoppingCarts;
import com.algaworks.algashop.ordering.domain.model.utility.DomainService;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import org.springframework.beans.factory.annotation.Autowired;

@DomainService
public class ShoppingService {

    @Autowired
    Customers customers;

    @Autowired
    ShoppingCarts shoppingCarts;

    public ShoppingCart startShopping(CustomerId customerId){

        if (!customers.exists(customerId)){
            throw new CustomerNotFoundException();

        }

        if (!shoppingCarts.ofCustomer(customerId).isPresent()){
            throw new CustomerAlreadyHaveShoppingCartException();
        }

        return ShoppingCart.startShopping(customerId);

    }

}
