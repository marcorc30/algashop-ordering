package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.repository.ShoppingCarts;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.ShoppingCartPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.ShoppingCartPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.ShoppingCartPersistenceEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional(readOnly = true)
public class ShoppingCartsPersistenceProvider implements ShoppingCarts {

    @Autowired
    private ShoppingCartPersistenceEntityRepository repository;

    @Autowired
    private ShoppingCartPersistenceEntityAssembler assembler;

    @Autowired
    private ShoppingCartPersistenceEntityDisassembler disassembler;

    @Override
    public Optional<ShoppingCart> ofCustomer(CustomerId customerId) {
        return Optional.empty();
    }

    @Override
    public void remove(ShoppingCart shoppingCart) {

    }

    @Override
    public void remove(ShoppingCartId shoppingCartId) {

    }

    @Override
    public Optional<ShoppingCart> ofId(ShoppingCartId shoppingCartId) {
        Optional<ShoppingCartPersistenceEntity> byId = repository.findById(shoppingCartId.value());
        Optional<ShoppingCart> shoppingCart = byId.map(scpe -> disassembler.fromEntity(scpe, new ShoppingCart()));
        return shoppingCart;
    }

    @Override
    public boolean exists(ShoppingCartId shoppingCartId) {
        return false;
    }

    @Override
    public void add(ShoppingCart shoppingCart) {

        ShoppingCartPersistenceEntity shoppingCartPersistenceEntity = assembler.fromDomain(shoppingCart);
        repository.saveAndFlush(shoppingCartPersistenceEntity);


    }

    @Override
    public long count() {
        return 0;
    }
}
