package kitchenpos.ui.dto.request;

public class MenuProductRequest {

    private Long productId;
    private int quantity;

    private MenuProductRequest() {
    }

    public MenuProductRequest(final Long productId, final int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "MenuProductRequest{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
