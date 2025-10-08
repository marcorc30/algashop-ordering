package com.algaworks.algashop.ordering.application.customer.management;

import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.application.utility.Mapper;
import com.algaworks.algashop.ordering.domain.model.customer.*;
import com.algaworks.algashop.ordering.domain.model.commons.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerManagementApplicationService {

//    @Autowired
    private final CustomerRegistrationService customerRegistration;

//    @Autowired
    private final Customers customers;

//    @Autowired
    private final Mapper mapper;


    @Transactional
    public UUID create(CustomerInput input){
        Objects.requireNonNull(input);

        AddressData address = input.getAddress();

        Customer customer = customerRegistration.register(
                new FullName(input.getFirstName(), input.getLastName()),
                new BirthDate(input.getBirthDate()),
                new Email(input.getEmail()),
                new Phone(input.getPhone()),
                new Document(input.getDocument()),
                input.getPromotionNotificationsAllowed(),
                Address.builder()
                        .zipCode(new ZipCode(address.getZipCode()))
                        .state(address.getState())
                        .city(address.getCity())
                        .neighborhood(address.getNeighborhood())
                        .street(address.getStreet())
                        .number(address.getNumber())
                        .complement(address.getComplement())
                        .build()
        );

        customers.add(customer);

        return customer.id().value();

    }

    @Transactional(readOnly = false)
    public CustomerOutput findById(UUID id){
        Objects.requireNonNull(id);

        Customer customer = customers.ofId(new CustomerId(id)).orElseThrow(() -> new CustomerNotFoundException());

        return mapper.convert(customer, CustomerOutput.class);


//        AddressData addressData = AddressData.builder()
//                .state(customer.address().state())
//                .city(customer.address().city())
//                .street(customer.address().street())
//                .complement(customer.address().complement())
//                .zipCode(customer.address().zipCode().value())
//                .neighborhood(customer.address().neighborhood())
//                .number(customer.address().number())
//                .build();
//
//        CustomerOutput customerOutput = CustomerOutput.builder()
//                .id(customer.id().value())
//                .firstName(customer.fullName().firstName())
//                .lastName(customer.fullName().lastName())
//                .email(customer.email().value())
//                .phone(customer.phone().value())
//                .document(customer.document().value())
//                .loyaltPoints(customer.loyaltPoints().value())
//                .birthDate(customer.birthDate().value())
//                .promotionNotificationsAllowed(customer.promotionNotificationsAllowed())
//                .registeredAt(customer.registeredAt())
//                .archivedAt(customer.archivedAt())
//                .address(addressData)
//                .build();

//        return customerOutput;

    }



}
