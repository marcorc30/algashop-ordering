package com.algaworks.algashop.ordering.infrastructure.utility.modelmapper;

import com.algaworks.algashop.ordering.application.customer.management.CustomerOutput;
import com.algaworks.algashop.ordering.application.utility.Mapper;
import com.algaworks.algashop.ordering.domain.model.commons.FullName;
import com.algaworks.algashop.ordering.domain.model.customer.BirthDate;
import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class ModelMapperConfig implements Mapper {

    private static final Converter<FullName,  String> fullNameToFirstNameConverter =
            new Converter<FullName, String>() {
                @Override
                public String convert(MappingContext<FullName, String> mappingContext) {
                    FullName fullName = mappingContext.getSource();
                    if (fullName == null){
                        return null;
                    }
                    return fullName.firstName();
                }
            };

    private static final Converter<FullName, String> fullNameToLastNameConverter =
            new Converter<FullName, String>() {
                @Override
                public String convert(MappingContext<FullName, String> mappingContext) {
                    FullName fullName = mappingContext.getSource();
                    if (fullName == null){
                        return null;
                    }
                    return fullName.lastName();
                }
            };

    private static final Converter<BirthDate, LocalDate> birthDateToLocalDate =
            new Converter<BirthDate, LocalDate>() {
                @Override
                public LocalDate convert(MappingContext<BirthDate, LocalDate> context) {
                    BirthDate birthDate = context.getSource();
                    if (birthDate == null){
                        return null;
                    }
                    return birthDate.value();
                }
            };


    @Override
    public <T> T convert(Object object, Class<T> destinationType) {
        ModelMapper modelMapper = new ModelMapper();
        configuration(modelMapper);
        return modelMapper.map(object, destinationType);
    }

    private void configuration(ModelMapper modelMapper) {
        modelMapper.getConfiguration()
                .setSourceNamingConvention(NamingConventions.NONE)
                .setDestinationNamingConvention(NamingConventions.NONE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(Customer.class, CustomerOutput.class)
                .addMappings(mapping -> mapping.using(fullNameToFirstNameConverter)
                        .map(Customer::fullName, CustomerOutput::setFirstName))
                .addMappings(mapping -> mapping.using(fullNameToLastNameConverter)
                        .map(Customer::fullName, CustomerOutput::setLastName))
                .addMappings(mapping -> mapping.using(birthDateToLocalDate)
                        .map(Customer::birthDate, CustomerOutput::setBirthDate));
    }
}
