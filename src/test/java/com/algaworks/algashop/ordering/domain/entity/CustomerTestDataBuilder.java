package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.*;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomerTestDataBuilder {

    private CustomerTestDataBuilder(){

    }

    public static Customer.BrandNewCustomerBuild brandNewCustomer(){
        return Customer.brandNew()
                        .fullName(new FullName("marco","cavalheiro"))
                        .birthDate(new BirthDate(LocalDate.of(1991, 7, 3)))
                        .email(new Email("marco@email.com"))
                        .phone(new Phone("55666656"))
                        .document(new Document("65665466545"))
                        .promotionNotificationsAllowed(false)
                        .address(Address.builder()
                                .street("teste")
                                .city("teste")
                                .complement("complemento")
                                .neighborhood("teste")
                                .number("546566")
                                .state("teste")
                                .zipCode(new ZipCode("12345"))
                                .build()
                        );


    }

    public static Customer.BrandExistingCustomerBuild existingAnonymyzedCustomer(){
        return Customer.brandExisting()
                .id(new CustomerId())
                .registeredAt(OffsetDateTime.now())
                .promotionNotificationsAllowed(true)
                .archived(false)
                .archivedAt(null)
                .fullName(new FullName("marco","cavalheiro"))
                .birthDate(new BirthDate(LocalDate.of(1991, 7, 3)))
                .email(new Email("marco@email.com"))
                .phone(new Phone("55666656"))
                .document(new Document("65665466545"))
                .address(Address.builder()
                        .street("teste")
                        .city("teste")
                        .complement("complemento")
                        .neighborhood("teste")
                        .number("546566")
                        .state("teste")
                        .zipCode(new ZipCode("12345"))
                        .build()
                );



    }

    public static Customer.BrandExistingCustomerBuild existingCustomer(){
        return Customer.brandExisting()
                .id(new CustomerId())
                .fullName(new FullName("Anonymous","Anonymous"))
                .birthDate(new BirthDate(LocalDate.of(1991, 7, 3)))
                .email(new Email("anonymous@anonymous.com"))
                .phone(new Phone("55666656"))
                .document(new Document("65665466545"))
                .promotionNotificationsAllowed(false)
                .archived(true)
                .registeredAt(OffsetDateTime.now())
                .loyaltyPoints(new LoyaltPoints(10))
                .address(Address.builder()
                        .street("teste")
                        .city("teste")
                        .complement("complemento")
                        .neighborhood("teste")
                        .number("546566")
                        .state("teste")
                        .zipCode(new ZipCode("12345"))
                        .build()
                );


    }
}
