package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerAlreadyHaveShoppingCartException;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerNotFoundException;
import com.algaworks.algashop.ordering.domain.model.customer.Customers;
import com.algaworks.algashop.ordering.domain.model.DomainService;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
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
