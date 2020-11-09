package kitchenpos.domain;

import java.util.List;

public class Menus {

    private List<Menu> menus;

    public Menus(final List<Menu> menus) {
        this.menus = menus;
    }

    public void validateMenusCount(final List<Long> menuIds) {
        if (menuIds.size() != menus.size()) {
            throw new IllegalArgumentException();
        }
    }

    public Menu findById(final Long menuId) {
        return menus.stream()
                .filter(menu -> menu.getId().equals(menuId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
