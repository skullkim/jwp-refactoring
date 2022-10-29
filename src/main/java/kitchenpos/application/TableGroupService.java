package kitchenpos.application;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import kitchenpos.application.dto.OrderTableIdDto;
import kitchenpos.application.dto.TableGroupCreationDto;
import kitchenpos.application.dto.TableGroupDto;
import kitchenpos.dao.OrderDao;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.dao.TableGroupDao;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.TableGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class TableGroupService {

    private final OrderDao orderDao;
    private final OrderTableDao orderTableDao;
    private final TableGroupDao tableGroupDao;

    public TableGroupService(final OrderDao orderDao,
                             final OrderTableDao orderTableDao,
                             final TableGroupDao tableGroupDao) {
        this.orderDao = orderDao;
        this.orderTableDao = orderTableDao;
        this.tableGroupDao = tableGroupDao;
    }

    @Transactional
    public TableGroupDto create(final TableGroupCreationDto tableGroupCreationDto) {
        List<Long> orderTableIds = tableGroupCreationDto.getOrderTableIds()
                .stream()
                .map(OrderTableIdDto::getId)
                .collect(Collectors.toList());
        final List<OrderTable> savedOrderTables = orderTableDao.findAllByIdIn(orderTableIds);
        if (orderTableIds.size() != savedOrderTables.size()) {
            throw new IllegalArgumentException();
        }

        final TableGroup tableGroup = new TableGroup(savedOrderTables, LocalDateTime.now());

        final TableGroup savedTableGroup = tableGroupDao.save(tableGroup);
        final Long tableGroupId = savedTableGroup.getId();

        final List<OrderTable> orderTables = savedOrderTables.stream()
                .map(orderTable -> orderTableDao.save(
                        new OrderTable(orderTable.getId(), tableGroupId, orderTable.getNumberOfGuests(), false)))
                .collect(Collectors.toList());

        return TableGroupDto.from(savedTableGroup.addTableGroups(orderTables));
    }

    @Transactional
    public void ungroupTable(final Long tableGroupId) {
        final List<OrderTable> orderTables = orderTableDao.findAllByTableGroupId(tableGroupId);

        final List<Long> orderTableIds = orderTables.stream()
                .map(OrderTable::getId)
                .collect(Collectors.toList());

        validateOrdersStatus(orderDao.findAllByOrderTableId(orderTableIds));

        for (final OrderTable orderTable : orderTables) {
            orderTableDao.save(new OrderTable(orderTable.getId(), null, orderTable.getNumberOfGuests(), false));
        }
    }

    private void validateOrdersStatus(final List<Order> orders) {
        if (isUnCompletionStatus(orders)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isUnCompletionStatus(final List<Order> orders) {
        return orders.stream()
                .anyMatch(order -> !order.isInCompletionStatus());
    }
}
