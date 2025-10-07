package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.product.ProductOutOfStockException;
import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.product.Product;
import com.algaworks.algashop.ordering.domain.model.product.ProductName;
import com.algaworks.algashop.ordering.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.domain.model.product.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ShoppingCartTest {

    @Test
    void dado_a_criacao_de_carrinho_vazio_entao_conferir_valores_e_quantidades_se_estao_zerados(){

        ShoppingCart shoppingCart = ShoppingCart.startShopping(new CustomerId());

        Assertions.assertThat(shoppingCart.totalAmount()).isEqualTo(Money.ZERO);
        Assertions.assertThat(shoppingCart.totalItens()).isEqualTo(Quantity.ZER0);


    }

    @Test
    void dado_um_carrinho_quando_adicionar_produto_sem_estoque_entao_lancar_excecao(){

        ShoppingCart shoppingCart = ShoppingCart.startShopping(new CustomerId());

        Product product = Product.builder()
                .id(new ProductId())
                .name(new ProductName("teste"))
                .inStock(false)
                .price(Money.ZERO)
                .build();

        Assertions.assertThatExceptionOfType(ProductOutOfStockException.class)
                        .isThrownBy(() -> shoppingCart.addItem(product, new Quantity(10)));

    }

    @Test
    void dado_um_carrinho_quando_incluir_mesmo_produto_duas_vezes_atualizar_valores(){

        ShoppingCart shoppingCart = ShoppingCart.startShopping(new CustomerId());

        Product product = Product.builder()
                .id(new ProductId())
                .name(new ProductName("teste"))
                .inStock(true)
                .price(Money.ZERO)
                .build();

        shoppingCart.addItem(product, new Quantity(10));


        shoppingCart.recalculateTotals();

        Assertions.assertThat(shoppingCart.totalItens()).isEqualTo(new Quantity(10));

    }

    @Test
    void dado_um_carrinho_quando_adicionar_dois_produtos_entao_atualizar_valores(){

        ShoppingCart shoppingCart = ShoppingCart.startShopping(new CustomerId());

        Product product = Product.builder()
                .id(new ProductId())
                .name(new ProductName("teste"))
                .inStock(true)
                .price(new Money("30.0"))
                .build();

        Product product2 = Product.builder()
                .id(new ProductId())
                .name(new ProductName("teste2"))
                .inStock(true)
                .price(new Money("25.0"))
                .build();

        shoppingCart.addItem(product, new Quantity(10));
        shoppingCart.addItem(product2, new Quantity(20));

        Assertions.assertThat(product2.price()).isEqualTo(new Money("25.0"));
        Assertions.assertThat(product.price()).isEqualTo(new Money("30.0"));

        Assertions.assertThat(shoppingCart.items().size()).isEqualTo(2);
        Assertions.assertThat(shoppingCart.totalItens()).isEqualTo(new Quantity(30));

          /*
        CORRIGIR ESSE TESTE -> O RESULTADO DEVE  SER 55 AO INVES DE 800
         */
        Assertions.assertThat(shoppingCart.totalAmount()).isEqualTo(new Money("800.0"));

    }

    @Test
    void dado_um_carrinho_quando_excluir_item_inexistente_entao_lancar_excecao(){

        ShoppingCart shoppingCart = ShoppingCart.startShopping(new CustomerId());

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> shoppingCart.removeItem(new ShoppingCartItemId()));
    }


    @Test
    void dado_um_carrinho_quando_esvaziar_os_items_entao_validar_valores(){

        ShoppingCart shoppingCart = ShoppingCart.startShopping(new CustomerId());

        Product product = Product.builder()
                .id(new ProductId())
                .name(new ProductName("teste"))
                .inStock(true)
                .price(new Money("30.0"))
                .build();

        Product product2 = Product.builder()
                .id(new ProductId())
                .name(new ProductName("teste2"))
                .inStock(true)
                .price(new Money("25.0"))
                .build();



        shoppingCart.addItem(product, new Quantity(10));
        shoppingCart.addItem(product2, new Quantity(20));

        shoppingCart.empty();

        Assertions.assertThat(shoppingCart.items().size()).isEqualTo(0);
        Assertions.assertThat(shoppingCart.totalAmount()).isEqualTo(Money.ZERO);
        Assertions.assertThat(shoppingCart.totalItens()).isEqualTo(Quantity.ZER0);


    }

}