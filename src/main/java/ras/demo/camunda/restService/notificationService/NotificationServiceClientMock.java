package ras.demo.camunda.restService.notificationService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ras.demo.camunda.restService.productService.model.ChangeStatusDTO;

import java.util.UUID;


@Slf4j
@Primary
@Service
@ConditionalOnProperty(name = "rest.notification-service.mock.enabled", matchIfMissing = false)
public class NotificationServiceClientMock implements NotificationService {

    @Override
    public void send(UUID orderId, ChangeStatusDTO changeStatusDTO) {

    }
}