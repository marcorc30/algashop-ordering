package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.domain.validator.FieldValidations;
import lombok.Builder;

import java.util.Objects;

public record Address(
        String street,
        String complement,
        String neighborhood,
        String number,
        String city,
        String state,
        ZipCode zipCode
) {

    @Builder(toBuilder = true)
    public Address {

        FieldValidations.requiredNonBlanckAndNonNull(street);
        FieldValidations.requiredNonBlanckAndNonNull(complement);
        FieldValidations.requiredNonBlanckAndNonNull(neighborhood);
        FieldValidations.requiredNonBlanckAndNonNull(number);
        FieldValidations.requiredNonBlanckAndNonNull(city);
        FieldValidations.requiredNonBlanckAndNonNull(state);
        Objects.requireNonNull(zipCode);
    }


}
