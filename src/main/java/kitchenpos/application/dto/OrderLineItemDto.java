package kitchenpos.application.dto;

import kitchenpos.domain.OrderLineItem;

public class OrderLineItemDto {

    private final Long id;
    private final Long orderId;
    private final Long menuId;
    private final long quantity;

    private OrderLineItemDto(final Long id, final Long orderId, final Long menuId, final long quantity) {
        this.id = id;
        this.orderId = orderId;
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public static OrderLineItemDto from(final OrderLineItem orderLineItem) {
        return new OrderLineItemDto(orderLineItem.getId(),
                orderLineItem.getOrderId(),
                orderLineItem.getMenuId(),
                orderLineItem.getQuantity());
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public long getQuantity() {
        return quantity;
    }
}