package kitchenpos.acceptance.common.fixture;

import static java.util.Map.entry;

import java.util.List;
import java.util.Map;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.Order;

public class RequestBody {

    public static final Map<String, Object> PRODUCT = Map.of(
            "name", "productName",
            "price", 1000);

    public static final Map<String, Object> MENU_GROUP = Map.of("name", "menuGroup");

    public static Map<String, Object> getMenuProductFixture(final Long productId, final Long menuGroupId) {
        final MenuProduct menuProduct = new MenuProduct();
        menuProduct.setQuantity(2);
        menuProduct.setProductId(productId);
        return Map.ofEntries(
                entry("name", "menu"),
                entry("price", 1000),
                entry("menuGroupId", menuGroupId),
                entry("menuProducts", List.of(menuProduct))
        );
    }

    public static final Map<String, Object> ORDER_TABLE_1 = Map.of(
            "numberOfGuests", 0,
            "empty", true
    );

    public static final Map<String, Object> NON_EMPTY_TABLE = Map.of(
            "numberOfGuests", 1,
            "empty", false
    );

    public static final Map<String, Object> ORDER_TABLE_2 = Map.of(
            "numberOfGuests", 0,
            "empty", true
    );

    public static Map<String, Object> getOrder(final Long menuId, final Long orderTableId) {
        return Map.of(
                "orderTableId", orderTableId,
                "orderLineItems", List.of(Map.of(
                    "menuId", menuId,
                    "quantity", 1
                )));
    }
}
