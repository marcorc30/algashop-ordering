package com.algaworks.algashop.ordering.domain.model.service;

import com.algaworks.algashop.ordering.domain.exception.ShoppingCartCantProceedToCheckoutException;
import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCartItem;
import com.algaworks.algashop.ordering.domain.model.utility.DomainService;
import com.algaworks.algashop.ordering.domain.model.valueobject.Billing;
import com.algaworks.algashop.ordering.domain.model.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.valueobject.Shipping;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;
import lombok.NoArgsConstructor;

import java.util.Set;

@DomainService
@NoArgsConstructor
public class CheckoutService {

    public Order checkout(ShoppingCart shoppingCart, Billing billing, Shipping shipping, PaymentMethod paymentMethod){

        if (shoppingCart.containsUnavailableItems() || shoppingCart.isEmpty()){
            throw new ShoppingCartCantProceedToCheckoutException();
        }

        Set<ShoppingCartItem> items = shoppingCart.items();

        Order order = Order.draft(shoppingCart.customerId());
        order.changeBilling(billing);
        order.changeShipping(shipping);
        order.changePaymentMethod(paymentMethod);

        shoppingCart.items()
                .stream()
                .forEach(i -> {
                    order.addItem(Product.builder()
                                    .price(i.price())
                                    .name(i.productName())
                                    .inStock(true)
                                    .id(new ProductId(i.id().value()))
                                    .build(),
                            i.quantity());
                });

        order.place();
        shoppingCart.empty();

        return order;
    }

}
