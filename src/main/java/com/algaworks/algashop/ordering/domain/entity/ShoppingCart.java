package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Product;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ShoppingCartId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ShoppingCartItemId;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ShoppingCart {

    private ShoppingCartId id;
    private CustomerId customerId;
    private Money totalAmount;
    private Quantity totalItens;
    private OffsetDateTime createdAt;
    private Set<ShoppingCartItem> items;

    @Builder(builderClassName = "ExistingShoppingCartBuilder", builderMethodName = "existing")
    public ShoppingCart(ShoppingCartId id, CustomerId customerId, Money totalAmount, Quantity totalItens,
                        OffsetDateTime createdAt, Set<ShoppingCartItem> items) {
        setId(id);
        setCustomerId(customerId);
        setTotalAmount(totalAmount);
        setTotalItens(totalItens);
        setCreatedAt(createdAt);
        setItems(items);
    }

    public static ShoppingCart startShopping(ShoppingCartId id){
        return new ShoppingCart(
                id,
                null,
                null,
                null,
                null,
                new HashSet<>()
        );
    }

    public void recalculateTotals(){

        BigDecimal totalAmount = this.items.stream().map(i -> i.totalAmount().value()).reduce(BigDecimal.ZERO, BigDecimal::add);
        Integer totalQuantity = this.items.stream().map(i -> i.quantity().value()).reduce(0, Integer::sum);

        setTotalAmount(new Money(totalAmount));
        setTotalItens(new Quantity(totalQuantity));

    }

    public ShoppingCartId id() {
        return id;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public Money totalAmount() {
        return totalAmount;
    }

    public Quantity totalItens() {
        return totalItens;
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public Set<ShoppingCartItem> items() {
        return items;
    }

    private void setId(ShoppingCartId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setCustomerId(CustomerId customerId) {
        this.customerId = customerId;
    }

    private void setTotalAmount(Money totalAmount) {
        Objects.requireNonNull(totalAmount);
        this.totalAmount = totalAmount;
    }

    private void setTotalItens(Quantity totalItens) {
        this.totalItens = totalItens;
    }

    private void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setItems(Set<ShoppingCartItem> items) {
        this.items = items;
    }

    public void empty(){
        items.clear();
        recalculateTotals();
    }

    public void addItem(Product product, Quantity quantity){

        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        ShoppingCartItem shoppingCartItem = ShoppingCartItem.brandNew()
                .id(new ShoppingCartItemId())
                .productId(new ProductId())
                .productName(product.name())
                .price(product.price())
                .build();

        this.items.add(shoppingCartItem);

        recalculateTotals();

    }

    public void removeItem(ShoppingCartItemId shoppingCartItemId){

        ShoppingCartItem shoppingCartItem = findItem(shoppingCartItemId);
        items.remove(shoppingCartItem);

        recalculateTotals();

    }

    public ShoppingCartItem findItem(ShoppingCartItemId shoppingCartItemId){
        return this.items.stream()
                .filter(i -> i.id().equals(shoppingCartItemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());

    }


    public boolean isEmpty(){
        return this.items.isEmpty();
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
