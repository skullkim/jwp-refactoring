package kitchenpos.application.dto;

import java.math.BigDecimal;
import kitchenpos.domain.Product;

public class ProductDto {

    private Long id;
    private String name;
    private BigDecimal price;

    private ProductDto() {}

    private ProductDto(final Long id, final String name, final BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductDto from(final Product product) {
        return new  ProductDto(product.getId(), product.getName(), product.getPrice());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
