package com.algaworks.algashop.ordering.domain.exception;

public class ErrorMessage {

    public static final String VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST = "BirthDate must be a past date";

    public static final String VALIDATION_ERROR_FULLNAME_IS_NULL = "FullName cannot be null";
    public static final String VALIDATION_ERROR_FULLNAME_IS_BLANK = "FullName cannot be blank";

    public static final String VALIDATION_ERROR_EMAIL_IS_INVALID = "Email is invalid";
    public static final String ERROR_CUSTOMER_ARCHIVED = "Customer archived cannot be changed";

    public static final String ERROR_ORDER_STATUS_CANNOT_BE_CHANGED = "Cannot change order %s status from %s to %s";
    public static final String ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_THE_PAST = "Order %s expected delivery date cannot be in the past";

    public static final String ERROR_ODER_CANNOT_BE_PLACED_HAS_NOT_ITEMS =  "Orders %s cannot be closed, it has no items";

    public static final String ERROR_ODER_CANNOT_BE_PLACED_HAS_NO_SHIPPING_INFO = "Order s cannot be closed, it has not shipping info";

    public static final String ERROR_ODER_CANNOT_BE_PLACED_HAS_BILLING_INFO = "Order s cannot be closed, it has not billing info";

    public static final String ERROR_ODER_CANNOT_BE_PLACED_INVALID_SHIPPING_COST = "Order s cannot be closed, it has not shipping cost info";

    public static final String ERROR_ODER_CANNOT_BE_PLACED_INVALID_DELIVERY_DATE = "Order s cannot be closed, it has not delivery date info";

    public static final String ERROR_ODER_CANNOT_BE_PLACED_NO_PAYMENT_METHOD = "Order s cannot be closed, it has not payment method info";

    public static final String ERROR_ORDER_DOES_NOT_CONTAIN_ITEM = "Order %s does not contain item %s";
    public static final String ERROR_PRODUCT_IS_OUT_OF_STOCK = "Product %s is out of stock";
    public static final String ERROR_ORDER_CANNOT_BE_EDITED = "Order %s with status %s cannot be edited";
    public static final String ERROR_ODER_CANNOT_BE_PAID = "Order %s cannot be paid";
    public static final String ERROR_ORDER_CANNOT_BE_CANCELED = "Order %s cannot be canceled";
}
