package com.algaworks.algashop.ordering.domain.model.customer;

import com.algaworks.algashop.ordering.domain.model.commons.*;
import com.algaworks.algashop.ordering.domain.model.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public final class CustomerRegistrationService {

    private final Customers customers;

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

        boolean emailUnique = customers.isEmailUnique(email, id);

        if (!emailUnique){
            throw new CustomerEmailIsInUseException();

        }

    }




}
