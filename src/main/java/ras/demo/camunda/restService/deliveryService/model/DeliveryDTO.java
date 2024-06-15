package ras.demo.camunda.restService.deliveryService.model;

import lombok.Data;

import java.util.UUID;

@Data
public class DeliveryDTO {
    private final String deliveryAddress;

    private final UUID orderId;
}
