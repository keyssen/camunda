package ras.demo.camunda.restService.productService.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) для изменения статуса заказа.
 */
@Data
public class ChangeStatusDTO {

    /**
     * Новый статус заказа.
     */
    @NotNull(message = "Status is required")
    private final OrderStatus status;

    private final LocalDateTime deliveryDateTime;
}
