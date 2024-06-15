package ras.demo.camunda.restService.deliveryService;

import ras.demo.camunda.restService.deliveryService.model.DeliveryDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public interface DeliveryService {
    LocalDateTime delivery(DeliveryDTO deliveryDTO);

    void cancel(UUID orderId);
}