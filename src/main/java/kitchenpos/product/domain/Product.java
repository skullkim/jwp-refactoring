package kitchenpos.product.domain;

import java.math.BigDecimal;
import kitchenpos.common.Price;

public class Product {

    private final Long id;
    private final String name;
    private final Price price;

    public Product(final Long id, final String name, final BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = new Price(price);
    }

    public Product(final String name, final BigDecimal price) {
        this(null, name, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }
}