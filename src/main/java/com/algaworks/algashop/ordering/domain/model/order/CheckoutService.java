package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartCantProceedToCheckoutException;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartItem;
import com.algaworks.algashop.ordering.domain.model.DomainService;
import com.algaworks.algashop.ordering.domain.model.product.Product;
import com.algaworks.algashop.ordering.domain.model.product.ProductId;
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
