package com.algaworks.algashop.ordering.domain.model.service;

import com.algaworks.algashop.ordering.domain.exception.CustomerEmailIsInUseException;
import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CustomerRegistrationService {

    private Customers customers;

    public Customer register(FullName fullName, BirthDate birthDate, Email email,
                             Phone phone, Document document, Boolean promotionNotificationsAllowed,
                             Address address){

        Customer customer = Customer.brandNew()
                .fullName(fullName)
                .birthDate(birthDate)
                .email(email)
                .phone(phone)
                .document(document)
                .promotionNotificationsAllowed(promotionNotificationsAllowed)
                .address(address)
                .build();

        verifyEmailUniqueness(customer.email(), customer.id());

        return customer;

    }

    public void changeEmail(Customer customer, Email email){
        verifyEmailUniqueness(email, customer.id());
        customer.changeEmail(email);
    }

    private void verifyEmailUniqueness(Email email, CustomerId id) {

        if (!customers.isEmailUnique(email, id)){
            throw new CustomerEmailIsInUseException();

        }

    }




}
