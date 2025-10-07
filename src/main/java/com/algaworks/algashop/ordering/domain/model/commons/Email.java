package com.algaworks.algashop.ordering.domain.model.commons;

import com.algaworks.algashop.ordering.domain.model.FieldValidations;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessage.VALIDATION_ERROR_EMAIL_IS_INVALID;

public record Email(String value) {

    public Email(String value) {
        Objects.requireNonNull(value);

        if (value.isBlank()){
            throw new IllegalArgumentException();
        }

        FieldValidations.requiresValidEmail(value, VALIDATION_ERROR_EMAIL_IS_INVALID);

        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }
}
