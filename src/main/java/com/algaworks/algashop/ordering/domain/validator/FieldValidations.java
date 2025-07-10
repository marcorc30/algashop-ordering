package com.algaworks.algashop.ordering.domain.validator;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public class FieldValidations {

    public FieldValidations(){

    }

    public static void requiresValidEmail(String email){
        requiresValidEmail(email, null);
    }

    public static void requiresValidEmail(String email, String messageError){
        Objects.requireNonNull(email, messageError);

        if (email.isBlank()){
            throw new IllegalArgumentException();
        }

        if (!EmailValidator.getInstance().isValid(email)){
            throw new IllegalArgumentException(messageError);
        }


    }
}
