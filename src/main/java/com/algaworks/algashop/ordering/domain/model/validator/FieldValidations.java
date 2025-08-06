package com.algaworks.algashop.ordering.domain.model.validator;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public class FieldValidations {

    public FieldValidations(){}


    public static void requiredNonBlanckAndNonNull(String value){
        requiredNonBlanckAndNonNull(value, "");
    }

    public static void requiredNonBlanckAndNonNull(String value, String message){
        Objects.requireNonNull(value);
        if (value.isBlank()){
            throw new IllegalArgumentException(message);
        }
    }

    public static void requiredNonBlanck(String value){
//        Objects.requireNonNull(value);
        if (value.isBlank()){
            throw new IllegalArgumentException(value);
        }
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
