package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.RemoveCapableRepository;
import com.algaworks.algashop.ordering.domain.model.Repository;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;

import java.util.Optional;

public interface ShoppingCarts extends Repository<ShoppingCart, ShoppingCartId>,
        RemoveCapableRepository<ShoppingCart, ShoppingCartId> {

    Optional<ShoppingCart> ofCustomer(CustomerId customerId);

}
