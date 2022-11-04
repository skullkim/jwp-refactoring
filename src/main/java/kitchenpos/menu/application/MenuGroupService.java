package kitchenpos.menu.application;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.menu.application.dto.MenuGroupCreationDto;
import kitchenpos.menu.application.dto.MenuGroupDto;
import kitchenpos.menu.domain.MenuGroupRepository;
import kitchenpos.menu.domain.MenuGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuGroupService {

    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroupDto create(final MenuGroupCreationDto menuGroupCreationDto) {
        final MenuGroup menuGroup = menuGroupRepository.save(MenuGroupCreationDto.toEntity(menuGroupCreationDto));
        return MenuGroupDto.from(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroupDto> getMenuGroups() {
        List<MenuGroup> menuGroups = menuGroupRepository.findAll();
        return menuGroups.stream()
                .map(MenuGroupDto::from)
                .collect(Collectors.toList());
    }
}
