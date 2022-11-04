package kitchenpos.product.ui.dto.request;

public class ProductCreationRequest {

    private String name;
    private int price;

    private ProductCreationRequest() {
    }

    public ProductCreationRequest(final String name, final int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ProductCreationRequest{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}