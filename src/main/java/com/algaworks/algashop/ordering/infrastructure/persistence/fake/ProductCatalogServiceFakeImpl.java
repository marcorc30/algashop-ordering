package com.algaworks.algashop.ordering.infrastructure.persistence.fake;

import com.algaworks.algashop.ordering.domain.model.product.ProductCatalogService;
import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.product.Product;
import com.algaworks.algashop.ordering.domain.model.product.ProductName;
import com.algaworks.algashop.ordering.domain.model.product.ProductId;

import java.util.Optional;

public class ProductCatalogServiceFakeImpl implements ProductCatalogService {
    @Override
    public Optional<Product> ofId(ProductId id) {

        Product product = Product.builder().id(id)
                .inStock(true)
                .name(new ProductName("notebook"))
                .price(new Money("3000"))
                .build();

        return Optional.of(product);
    }
}
