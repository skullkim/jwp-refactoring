package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.util.List;
import kitchenpos.order.application.TableService;
import kitchenpos.order.application.dto.OrderTableCreationDto;
import kitchenpos.order.application.dto.OrderTableDto;
import kitchenpos.common.annotation.SpringTestWithData;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderTableRepository;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItem;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.ui.dto.request.OrderTableCreationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("TableService 는 ")
@SpringTestWithData
class TableServiceTest {

    @Autowired
    private TableService tableService;

    @Autowired
    private OrderTableRepository orderTableRepository;

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("주문 테이블을 생성한다.")
    @Test
    void createOrderTable() {
        final OrderTableCreationRequest orderTableCreationRequest = new OrderTableCreationRequest(0, true);
        final OrderTableCreationDto orderTableCreationDto = OrderTableCreationDto.from(orderTableCreationRequest);

        final OrderTableDto orderTableDto = tableService.createOrderTable(orderTableCreationDto);

        assertAll(
                () -> assertThat(orderTableDto.getId()).isGreaterThanOrEqualTo(1L),
                () -> assertThat(orderTableDto.getNumberOfGuests()).isEqualTo(0)
        );
    }

    @DisplayName("주문 테이블 항목을 조회한다.")
    @Test
    void getOrderTables() {
        final OrderTableCreationRequest orderTableCreationRequest = new OrderTableCreationRequest(0, true);
        final OrderTableCreationDto orderTableCreationDto = OrderTableCreationDto.from(orderTableCreationRequest);
        final OrderTableDto orderTableDto = tableService.createOrderTable(orderTableCreationDto);

        final List<OrderTableDto> orderTableDtos = tableService.getOrderTables();

        assertAll(
                () -> assertThat(orderTableDtos.size()).isEqualTo(1),
                () -> assertThat(orderTableDtos.get(0).getId()).isEqualTo(orderTableDto.getId())
        );
    }

    @DisplayName("주문 테이블 비어있음 여부를 변경한다.")
    @Test
    void changeEmpty() {
        final OrderTable orderTable = new OrderTable(0, true);
        final OrderTable savedOrderTable = orderTableRepository.save(orderTable);
        orderRepository.save(new Order(savedOrderTable.getId(), OrderStatus.COMPLETION, LocalDateTime.now(),
                List.of(new OrderLineItem(1L, 1L))));
        final OrderTableDto orderTableDto = tableService.changeEmpty(savedOrderTable.getId(), false);

        assertThat(orderTableDto.isEmpty()).isFalse();
    }

    @DisplayName("주문 테이블의 인원 수를 변경한다.")
    @Test
    void changeNumberOfGuests() {
        final OrderTable orderTable = new OrderTable(0, true);
        final OrderTable savedOrderTable = orderTableRepository.save(orderTable);
        orderRepository.save(new Order(savedOrderTable.getId(), OrderStatus.COMPLETION, LocalDateTime.now(),
                List.of(new OrderLineItem(1L, 1L))));
        tableService.changeEmpty(savedOrderTable.getId(), false);
        final OrderTableDto orderTableDto = tableService.changeNumberOfGuests(savedOrderTable.getId(), 3);

        assertThat(orderTableDto.getNumberOfGuests()).isEqualTo(3);
    }
}
