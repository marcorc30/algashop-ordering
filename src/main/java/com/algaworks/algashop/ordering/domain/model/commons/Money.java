package com.algaworks.algashop.ordering.domain.model.commons;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(BigDecimal value) implements Comparable<BigDecimal> {

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money(String value){
        this(new BigDecimal(value));
    }

    public Money(BigDecimal value) {
        Objects.requireNonNull(value);

        if (value.signum() < 0){
            throw new IllegalArgumentException();
        }

        this.value = value.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Money multiply(Quantity quantity){
        if (quantity.value() < 1){
            throw new IllegalArgumentException();
        }
        var multiply =  this.value.multiply(new BigDecimal(quantity.value()));
        return new Money(multiply);
    }

    public Money divide(Money other){
        return new Money(this.value.divide(other.value));
    }

    public Money add(Money money){
        return new Money(this.value.add(money.value));
    }

    @Override
    public int compareTo(BigDecimal o) {
        return this.value().compareTo(o);
    }
}
