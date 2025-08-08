package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ShoppingCartItemId;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

public class ShoppingCart implements AggregateRoot<ShoppingCartId>{

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

    public static ShoppingCart startShopping(CustomerId customerId){
        return new ShoppingCart(
                new ShoppingCartId(),
                customerId,
                Money.ZERO,
                Quantity.ZER0,
                OffsetDateTime.now(),
                new HashSet<>()
        );
    }

    public void recalculateTotals(){

        BigDecimal totalAmount = this.items.stream().map(i -> i.price().value()).reduce(BigDecimal.ZERO, BigDecimal::add);
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

        return Collections.unmodifiableSet(items);
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
        this.totalAmount = Money.ZERO;
        this.totalItens = Quantity.ZER0;
//        recalculateTotals();
    }

    public void addItem(Product product, Quantity quantity){

        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        product.checkOutOfStock();


        ShoppingCartItem shoppingCartItem = ShoppingCartItem.brandNew()
                .id(new ShoppingCartItemId())
                .shoppingCartId(this.id)
                .productId(new ProductId())
                .productName(product.name())
                .price(product.price())
                .quantity(quantity)
                .available(true)
                .build();

        searchItemByProduct(product.id())
                .ifPresentOrElse(i -> updateItem(i, product, quantity), () -> insertItem(shoppingCartItem));

        this.items.add(shoppingCartItem);

        recalculateTotals();

    }

    private void insertItem(ShoppingCartItem shoppingCartItem) {
        this.items.add(shoppingCartItem);
    }

    private void updateItem(ShoppingCartItem shoppingCartItem, Product product, Quantity quantity) {
        shoppingCartItem.refresh(product);
        shoppingCartItem.changeQuantity(shoppingCartItem.quantity().add(quantity));
    }

    public Optional<ShoppingCartItem> searchItemByProduct(ProductId productId){

        Objects.requireNonNull(productId);

        return this.items
                .stream()
                .filter(s -> s.productId().equals(productId))
                .findFirst();



    }

    public void removeItem(ShoppingCartItemId shoppingCartItemId){

        ShoppingCartItem shoppingCartItem = this.findItem(shoppingCartItemId);
        this.items.remove(shoppingCartItem);

        recalculateTotals();

    }

    public void refreshItem(Product product){
        Objects.requireNonNull(product);

        ShoppingCartItem shoppingCartItem = findItem(product.id());
        shoppingCartItem.refresh(product);
        this.recalculateTotals();



//        product.

    }

    public ShoppingCartItem findItem(ShoppingCartItemId shoppingCartItemId){
        return this.items
                .stream()
                .filter(i -> i.id().equals(shoppingCartItemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());

    }

    public ShoppingCartItem findItem(ProductId productId){
        return  this.items
                .stream()
                .filter(i -> i.productId().equals(productId)).findAny().orElseThrow(() -> new IllegalArgumentException());


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
