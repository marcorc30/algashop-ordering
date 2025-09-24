package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCartTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.ShoppingCartPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.ShoppingCartPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({ShoppingCartsPersistenceProvider.class,
        ShoppingCartPersistenceEntityAssembler.class,
        ShoppingCartPersistenceEntityDisassembler.class})
class ShoppingCartsPersistenceProviderTest {

    private ShoppingCartsPersistenceProvider shoppingCartsPersistenceProvider;

    @Autowired
    ShoppingCartsPersistenceProviderTest(ShoppingCartsPersistenceProvider shoppingCartsPersistenceProvider) {
        this.shoppingCartsPersistenceProvider = shoppingCartsPersistenceProvider;
    }



    @Test
    void gravarERecuperarPorId(){

        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        ShoppingCartId id = shoppingCart.id();

        shoppingCartsPersistenceProvider.add(shoppingCart);
        Optional<ShoppingCart> shoppingCart1 = shoppingCartsPersistenceProvider.ofId(id);


        Assertions.assertThat(shoppingCart1).isNotNull();
        Assertions.assertThat(shoppingCart1.get().id()).isNotNull();
        Assertions.assertThat(id).isEqualTo(shoppingCart1.get().id());

        Assertions.assertThat(shoppingCart1.get().items().size()).isEqualTo(1);

    }

    @Test
    void gravarERecuperarPorIdDoCliente(){

    }

}