package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Product;
import com.algaworks.algashop.ordering.domain.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ShoppingCartId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ShoppingCartItemId;
import lombok.Builder;

import java.util.Objects;

public class ShoppingCartItem {

    private ShoppingCartItemId id;
    private ShoppingCartId shoppingCartId;
    private ProductId productId;
    private ProductName productName;
    private Money price;
    private Quantity quantity;
    private Money totalAmount;
    private Boolean available;


    @Builder(builderClassName = "ExistingShoppingCartItem", builderMethodName = "existing")
    public ShoppingCartItem(ShoppingCartItemId id, ShoppingCartId shoppingCartId, ProductId productId,
                            ProductName productName, Money price, Quantity quantity,
                            Money totalAmount, Boolean available) {
        setId(id);
        setShoppingCartId(shoppingCartId);
        setProductId(productId);
        setProductName(productName);
        setPrice(price);
        setQuantity(quantity);
        setTotalAmount(totalAmount);
        setAvailable(available);
    }

    @Builder(builderClassName = "BrandNewShoppingItemBuilder", builderMethodName = "brandNew")
    private ShoppingCartItem(ShoppingCartItemId id, ShoppingCartId shoppingCartId, ProductId productId,
                                                   ProductName productName, Money price, Quantity quantity, Boolean available){

        this(new ShoppingCartItemId(), shoppingCartId, productId, productName, price, quantity, Money.ZERO, available);


    }

    public ShoppingCartItemId id() {
        return id;
    }

    public ShoppingCartId shoppingCartId() {
        return shoppingCartId;
    }

    public ProductId productId() {
        return productId;
    }

    public ProductName productName() {
        return productName;
    }

    public Money price() {
        return price;
    }

    public Quantity quantity() {
        return quantity;
    }

    public Money totalAmount() {
        return totalAmount;
    }

    public Boolean available() {
        return available;
    }

    private void setId(ShoppingCartItemId id) {
        this.id = id;
    }

    private void setShoppingCartId(ShoppingCartId shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    private void setProductId(ProductId productId) {
        this.productId = productId;
    }

    private void setProductName(ProductName productName) {
        this.productName = productName;
    }

    private void setPrice(Money price) {
        this.price = price;
    }

    private void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    private void setTotalAmount(Money totalAmount) {
        this.totalAmount = totalAmount;
    }

    private void setAvailable(Boolean available) {
        this.available = available;
    }

    public void refresh(Product product){
        Objects.requireNonNull(product);
        Objects.requireNonNull(product.id());

        if (product.id().equals(this.productId)){
            throw new IllegalArgumentException();
        }

        this.setPrice(product.price());
        this.setAvailable(product.inStock());
        this.setProductName(product.name());
        this.recalculateTotals();

    }

    public void changeQuantity(Quantity quantity){

        Objects.requireNonNull(quantity);

        this.setQuantity(quantity);
        this.recalculateTotals();

    }

    private void recalculateTotals() {
        this.setTotalAmount(this.price.multiply(this.quantity));

    }



}
