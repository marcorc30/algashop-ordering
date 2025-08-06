package com.algaworks.algashop.ordering.domain.model.exception;



public class CustomerArchivedException extends DomainException{


    public CustomerArchivedException(String message, Throwable cause) {
        super(ErrorMessage.ERROR_CUSTOMER_ARCHIVED, cause);
    }

    public CustomerArchivedException() {
        super(ErrorMessage.ERROR_CUSTOMER_ARCHIVED);
    }
}
