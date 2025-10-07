package com.algaworks.algashop.ordering.domain.model.customer;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessage.*;

import com.algaworks.algashop.ordering.domain.model.AggregateRoot;
import com.algaworks.algashop.ordering.domain.model.commons.*;
import com.algaworks.algashop.ordering.domain.model.FieldValidations;
import lombok.Builder;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

/*
Observacoes

* Não usaremos o padrao java bean (classes anemicas e sim classes fortes)
* Essa domain entity nao eh baseada em banco de dados (nao devemos orientar nosso modelo baseada no banco)
* Domain entity precisa ter comportamentos e nao precisa ser imutavel
* Deve nao precisa ter todos os atributos mutaveis (sets para todo lado)
* Deve conter validacoes
* Pode conter Value Objects (cidade, endereco, email) -> nao sao persistidos isoladamente dentro dos DB. VO <> DTO
    * Sao imutaveis
    * Nap tem conceito de identidade unica
    * Comparaveis pelos dados
    * Dados e comportamentos ligados ao dominio
    * Possuem validacao e regra de negocio

 */

public class Customer implements AggregateRoot<CustomerId> {

    private CustomerId id;
    private FullName fullName;
    private BirthDate birthDate;
    private Email email;
    private Phone phone;
    private Document document;
    private Boolean promotionNotificationsAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private LoyaltPoints loyaltPoints;
    private Address address;
    private Long version;



    /*
  Usando o padrao STATIC FACTOTY METHOD para criar novos customers e para alterar cliente existente
   */
    @Builder(builderClassName = "BrandNewCustomerBuild", builderMethodName = "brandNew")
    private static Customer createBrandNew(FullName fullName, BirthDate birthDate, Email email,
                                    Phone phone, Document document, Boolean promotionNotificationsAllowed,
                                    Address address){

        return new Customer(
                new CustomerId(),
                fullName,
                birthDate,
                email,
                phone,
                document,
                promotionNotificationsAllowed,
                false,
                OffsetDateTime.now(),
                null,
                LoyaltPoints.ZERO,
                address);
    }

    @Builder(builderClassName = "ExistingCustomerBuild", builderMethodName = "existing")
    private Customer(CustomerId id, Long version, FullName fullName, BirthDate birthDate, Email email, Phone phone,
                     Document document, Boolean promotionNotificationsAllowed, Boolean archived,
                     OffsetDateTime registeredAt, OffsetDateTime archivedAt, LoyaltPoints loyaltyPoints, Address address) {
        this.setId(id);
        this.setVersion(version);
        this.setFullName(fullName);
        this.setBirthDate(birthDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificationsAllowed(promotionNotificationsAllowed);
        this.setArchived(archived);
        this.setRegisteredAt(registeredAt);
        this.setArchivedAt(archivedAt);
        this.setLoyaltPoints(loyaltyPoints);
        this.setAddress(address);
    }

//    @Builder(builderClassName = "BrandExistingCustomerBuild", builderMethodName = "brandExisting")
//    private static Customer createExisting(CustomerId id, FullName fullName, BirthDate birthDate, Email email, Phone phone,
//                                   Document document, Boolean promotionNotificationsAllowed, Boolean archived,
//                                   OffsetDateTime registeredAt, OffsetDateTime archivedAt, LoyaltPoints loyaltyPoints, Address address){
//
//
//        return new Customer(
//                id,
//                fullName,
//                birthDate,
//                email,
//                phone,
//                document,
//                promotionNotificationsAllowed,
//                archived,
//                registeredAt,
//                archivedAt,
//                loyaltyPoints,
//                address
//        );
//    }


    private Customer(CustomerId id, FullName fullName, BirthDate birthDate, Email email, Phone phone,
                    Document document, Boolean promotionNotificationsAllowed, Boolean archived,
                    OffsetDateTime registeredAt, OffsetDateTime archivedAt, LoyaltPoints loyaltyPoints, Address address) {
        this.setId(new CustomerId());
        this.setFullName(fullName);
        this.setBirthDate(birthDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificationsAllowed(promotionNotificationsAllowed);
        this.setArchived(archived);
        this.setRegisteredAt(registeredAt);
        this.setArchivedAt(archivedAt);
        this.setLoyaltPoints(loyaltyPoints);
        this.setAddress(address);
    }

    public Customer(Email email) {

        this.setEmail(email);

    }

    public void addLoyaltyPoints(LoyaltPoints points){
        verifyIfChangeable();

        this.setLoyaltPoints(this.loyaltPoints().add(points));

    }

    public void archive(){

        verifyIfChangeable();

        this.setArchived(true);
        this.setArchivedAt(OffsetDateTime.now());
        this.setFullName(new FullName("anonymous", "anonymous"));
        this.setPhone(new Phone("000-000-0000"));
        this.setDocument(new Document("000-00-0000"));
        this.setEmail(new Email(UUID.randomUUID() + "@anonymous.com"));
        this.setBirthDate(null);
        this.setPromotionNotificationsAllowed(false);

        Address.AddressBuilder builder = this.address().toBuilder();
        this.setAddress(builder.number("Anonymized").complement("SEM COMPLEMENTO").build());


    }

    private void verifyIfChangeable() {
        if (this.isArchived()){
            throw new CustomerArchivedException();
        }
    }

    public void enablePromotionNotifications(){
        verifyIfChangeable();
        this.setPromotionNotificationsAllowed(true);

    }

    public void disablePromotionNotifications(){
        verifyIfChangeable();
        this.setPromotionNotificationsAllowed(false);

    }

    public void changeName(FullName fullName){
        verifyIfChangeable();
        this.setFullName(fullName);

    }

    public void changeEmail(Email email){
        verifyIfChangeable();
        this.setEmail(email);

    }

    public void changePhone(Phone phone){
        verifyIfChangeable();
        this.setPhone(phone);

    }

    public void changeAddress(Address address){
        verifyIfChangeable();
        this.setAddress(address);
    }

    /*
    metodos abaixo do tipo cqs
    CQS, ou Separação Comando-Consulta, nao alteram o estado
     */
    public CustomerId id() {
        return id;
    }

    public FullName fullName() {
        return fullName;
    }

    public BirthDate birthDate() {
        return birthDate;
    }

    public Email email() {
        return email;
    }

    public Phone phone() {
        return phone;
    }

    public Document document() {
        return document;
    }

    public Boolean isPromotionNotificationsAllowed() {
        return promotionNotificationsAllowed;
    }

    public Boolean isArchived() {
        return archived;
    }

    public OffsetDateTime registeredAt() {
        return registeredAt;
    }

    public OffsetDateTime archivedAt() {
        return archivedAt;
    }

    public LoyaltPoints loyaltPoints() {
        return loyaltPoints;
    }

    public Address address(){
        return address;
    }

    public Boolean promotionNotificationsAllowed() {
        return promotionNotificationsAllowed;
    }

    public Boolean archived() {
        return archived;
    }

    public Long version() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }



    private void setId(CustomerId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setFullName(FullName fullName) {
        Objects.requireNonNull(fullName, VALIDATION_ERROR_FULLNAME_IS_NULL);

// codigo abaixo nao necessario, pois ja eh tratado no value object FullName
//        if (fullName.firstName().isBlank()){
//            throw new IllegalArgumentException(VALIDATION_ERROR_FULLNAME_IS_BLANK);
//        }
//
//        if (fullName.lastName().isBlank()){
//            throw new IllegalArgumentException(VALIDATION_ERROR_FULLNAME_IS_BLANK);
//        }

        this.fullName = fullName;
    }

    private void setBirthDate(BirthDate birthDate) {
        if (birthDate == null){
            this.birthDate = null;
            return;
        }
        if (birthDate.value().isAfter((LocalDate.now()))){
            throw new IllegalArgumentException(VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST);
        }
        this.birthDate = birthDate;
    }

    private void setEmail(Email email) {
//        Objects.requireNonNull(email);
//        if (email.isBlank()){
//            throw new IllegalArgumentException();
//        }
//
//        if (!EmailValidator.getInstance().isValid(email)){
//            throw new IllegalArgumentException();
//        }
        FieldValidations.requiresValidEmail(email.value(), VALIDATION_ERROR_EMAIL_IS_INVALID);
        this.email = email;
    }


    private void setPhone(Phone phone) {
        Objects.requireNonNull(phone);
        this.phone = phone;
    }

    private void setDocument(Document document) {
        Objects.requireNonNull(document);
        this.document = document;
    }

    private void setPromotionNotificationsAllowed(Boolean promotionNotificationsAllowed) {
        Objects.requireNonNull(promotionNotificationsAllowed);
        this.promotionNotificationsAllowed = promotionNotificationsAllowed;
    }

    private void setArchived(Boolean archived) {
        Objects.requireNonNull(archived);
        this.archived = archived;
    }

    private void setRegisteredAt(OffsetDateTime registeredAt) {
        Objects.requireNonNull(registeredAt);
        this.registeredAt = registeredAt;
    }

    private void setArchivedAt(OffsetDateTime archivedAt) {
        this.archivedAt = archivedAt;
    }

    private void setLoyaltPoints(LoyaltPoints loyaltPoints) {
        Objects.requireNonNull(loyaltPoints);
        this.loyaltPoints = loyaltPoints;
    }

    private void setAddress(Address address){
        Objects.requireNonNull(address);
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
