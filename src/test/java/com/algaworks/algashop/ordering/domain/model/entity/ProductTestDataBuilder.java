package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;

public class ProductTestDataBuilder {

    private ProductTestDataBuilder(){

    }

    public static Product.ProductBuilder aProduct(){
//        return new Product(new ProductId(), new ProductName("produto teste"), new Money("10.0"), true);
        return Product. builder()
                .id(new ProductId())
                .price(new Money("10.0"))
                .name(new ProductName("produto teste"))
                .inStock(true);
    }

    public static Product.ProductBuilder aProductUnavailable(){

        return Product.builder()
                .id(new ProductId())
                .price(new Money("20.0"))
                .name(new ProductName("produto teste indisponivel"))
                .inStock(false);
    }


}
