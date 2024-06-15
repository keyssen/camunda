package ras.demo.camunda.restService.notificationService;

import ras.demo.camunda.restService.productService.model.ChangeStatusDTO;

import java.util.UUID;

public interface NotificationService {

    void send(UUID orderId, ChangeStatusDTO changeStatusDTO);
}