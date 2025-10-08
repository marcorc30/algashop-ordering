package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartId;
import com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart.ShoppingCartPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart.ShoppingCartPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart.ShoppingCartsPersistenceProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

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



//    @Test
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