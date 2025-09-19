package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.exception.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderItemId;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;

public class Order implements AggregateRoot<OrderId>{

    private OrderId id;

    //adicionar uma referencia para o customer (somente o id, e nao o objeto)
    private CustomerId customerId;

    private Money totalAmount;
    private Quantity quantity;

    private OffsetDateTime placedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime readyAt;

    private Billing billing;
    private Shipping shipping;

    private OrderStatus status;
    private PaymentMethod paymentMethod;

    private Set<OrderItem> items;

    private Long version;

    @Builder(builderClassName = "ExistingOrderBuilder",builderMethodName = "existing")
    private Order(OrderId id, Long version, CustomerId customerId, Money totalAmount,
                  Quantity quantity, OffsetDateTime placedAt, OffsetDateTime paidAt,
                  OffsetDateTime canceledAt, OffsetDateTime readyAt, Billing billing,
                  Shipping shipping, OrderStatus status, PaymentMethod paymentMethod,
                  Set<OrderItem> items) {
        this.setId(id);
        this.setVersion(version);
        this.setCustomerId(customerId);
        this.setTotalAmount(totalAmount);
        this.setQuantity(quantity);
        this.setPlacedAt(placedAt);
        this.setPaidAt(paidAt);
        this.setCanceledAt(canceledAt);
        this.setReadyAt(readyAt);
        this.setBilling(billing);
        this.setShipping(shipping);
        this.setStatus(status);
        this.setPaymentMethod(paymentMethod);
        this.setItems(items);
    }

    public static Order draft(CustomerId customerId){
       return new Order(
               new OrderId(),
               null,
               customerId,
               Money.ZERO,
               Quantity.ZER0,
               null,
               null,
               null,
               null,
               null,
               null,
               OrderStatus.DRAFT,
               null,
               new HashSet<>()
        );
    }

    public OrderId id() {
        return id;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public Money totalAmount() {
        return totalAmount;
    }

    public Quantity quantity() {
        return quantity;
    }

    public OffsetDateTime placedAt() {
        return placedAt;
    }

    public OffsetDateTime paidAt() {
        return paidAt;
    }

    public OffsetDateTime canceledAt() {
        return canceledAt;
    }

    public OffsetDateTime readyAt() {
        return readyAt;
    }

    public Billing billingInfo() {
        return billing;
    }

    public Shipping shippingInfo() {
        return shipping;
    }

    public OrderStatus status() {
        return status;
    }

    public PaymentMethod paymentMethod() {
        return paymentMethod;
    }


    public Billing billing() {
        return billing;
    }

    public Shipping shipping() {
        return shipping;
    }

    public Set<OrderItem> items() {
        return Collections.unmodifiableSet(this.items);
    }

    public void changePaymentMethod(PaymentMethod paymentMethod){
        this.verifyIfChangeable();
        Objects.requireNonNull(paymentMethod);
        this.setPaymentMethod(paymentMethod);
    }

    public void changeBilling(Billing billing){
        this.verifyIfChangeable();
        Objects.requireNonNull(billing);
        this.setBilling(billing);
    }

    public void changeShipping(Shipping shipping){
        this.verifyIfChangeable();
        Objects.requireNonNull(shipping);

        if (shipping.expectedDate().isBefore(LocalDate.now())){
            throw new OrderInvalidShippingDeliveryDateException(this.id());
        }

        this.setShipping(shipping);
    }


    public void changeItemQuantity(OrderItemId orderItemId, Quantity quantity){
        this.verifyIfChangeable();
        Objects.requireNonNull(orderItemId);
        Objects.requireNonNull(quantity);

        OrderItem orderItem = this.findOrderItem(orderItemId);
        orderItem.changeQuantity(quantity);

        this.recalculateTotals();

    }

    private OrderItem findOrderItem(OrderItemId orderItemId) {

        Objects.requireNonNull(orderItemId);

        return this.items()
                .stream()
                .filter(i -> i.id().equals(orderItemId))
                .findFirst()
                .orElseThrow(() -> new OrderDoesNotContainOrderItemException(this.id, orderItemId));
    }


    public void markAsPaid(){

        this.setPaidAt(OffsetDateTime.now());
        changeStatus(OrderStatus.PAID);

    }

    public void markAsReady(){
        this.setReadyAt(OffsetDateTime.now());
        changeStatus(OrderStatus.READY);
    }


    public void place(){
        
        this.verifyIfCanChangeToPlace();
        this.setPlacedAt(OffsetDateTime.now());
        changeStatus(OrderStatus.PLACED);

    }


    public boolean isDraft(){
        return OrderStatus.DRAFT.equals(this.status());
    }

    public boolean isPlaced(){
        return OrderStatus.PLACED.equals(this.status());
    }

    public boolean isPaid(){
        return OrderStatus.PAID.equals(this.status);
    }

    public boolean isRead(){
        return OrderStatus.READY.equals(this.status);
    }

    public void addItem(Product product, Quantity quantity){

        this.verifyIfChangeable();

        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        product.checkOutOfStock();

        OrderItem orderItem = OrderItem.brandNew()
                .orderId(this.id)
                .quantity(quantity)
                .product(product)
                .build();

        this.items.add(orderItem);

        recalculateTotals();

    }

    public void cancel(){
        if (isCanceled()){
            throw new OrderCannotBeCanceledException(this.id());
        }

        setCanceledAt(OffsetDateTime.now());
        changeStatus(OrderStatus.CANCELED);
    }

    public boolean isCanceled(){
        return OrderStatus.CANCELED.equals(this.status());
    }

    private void verifyIfChangeable(){
        if (!this.isDraft()){
            throw new OrderCannotBeEditedException(this.id(), this.status());
        }
    }

//    private void verifyIfChangeToReady


    private void verifyIfCanChangeToPlace() {
        if (this.items().isEmpty()){
            throw OrderCannotBePlacedException.noItems(this.id);
        }

        if (this.shippingInfo() == null){
            throw OrderCannotBePlacedException.noShippingInfo(this.id);
        }

        if (this.billingInfo() == null){
            throw OrderCannotBePlacedException.noBillingInfo(this.id);
        }

        if (this.paymentMethod() == null){
            throw OrderCannotBePlacedException.noPaymentMethod(this.id);
        }


    }


    private void changeStatus(OrderStatus newStatus) {
        Objects.requireNonNull(newStatus);

        if (this.status().canNotChangeToStatus(newStatus)){
            throw new OrderStatusCannotBeChangeException(this.id(), this.status(), newStatus);
        }

        this.setStatus(newStatus);

    }




    public void removeItem(OrderItemId id){
        verifyIfChangeable();
        Objects.requireNonNull(id);

        OrderItem orderItem = findOrderItem(id);

        this.items.remove(orderItem);

        recalculateTotals();

    }


    private void recalculateTotals() {

        BigDecimal totalItensAmount = this.items.stream().map(i -> i.totalAmount().value())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Integer totalQuantity = this.items.stream().map(i -> i.quantity().value())
                .reduce(0, Integer::sum);


        BigDecimal shippingCost;

        if (this.shipping() == null){
            shippingCost = BigDecimal.ZERO;
        }else{
            shippingCost = this.shipping().cost().value();
        }

        BigDecimal totalAmount = totalItensAmount.add(shippingCost);

        this.setTotalAmount(new Money(totalAmount));
        this.setQuantity(new Quantity(totalQuantity));

    }

    private void setId(OrderId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setCustomerId(CustomerId customerId) {
        Objects.requireNonNull(customerId);
        this.customerId = customerId;
    }

    private void setTotalAmount(Money totalAmount) {
        Objects.requireNonNull(totalAmount);
        this.totalAmount = totalAmount;
    }

    private void setQuantity(Quantity quantity) {
        Objects.requireNonNull(quantity);
        this.quantity = quantity;
    }

    private void setPlacedAt(OffsetDateTime placedAt) {
        this.placedAt = placedAt;
    }

    private void setPaidAt(OffsetDateTime paidAt) {
        this.paidAt = paidAt;
    }

    private void setCanceledAt(OffsetDateTime canceledAt) {
        this.canceledAt = canceledAt;
    }

    private void setReadyAt(OffsetDateTime readyAt) {
        this.readyAt = readyAt;
    }

    private void setBilling(Billing billing) {
        this.billing = billing;
    }

    private void setShipping(Shipping shipping) {
//        Objects.requireNonNull(shippingInfo);
        this.shipping = shipping;
    }

    private void setStatus(OrderStatus status) {
        Objects.requireNonNull(status);
        this.status = status;
    }

    private void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    private void setItems(Set<OrderItem> items) {
        Objects.requireNonNull(items);
        this.items = items;
    }

    public Long version() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
