package com.algaworks.algashop.ordering.infrastructure.persistence.fake;

import com.algaworks.algashop.ordering.domain.model.service.ProductCatalogService;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;

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
