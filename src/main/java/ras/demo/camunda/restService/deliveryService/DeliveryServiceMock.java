package ras.demo.camunda.restService.deliveryService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ras.demo.camunda.restService.deliveryService.model.DeliveryDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Primary
@Service
@ConditionalOnProperty(name = "rest.delivery-service.mock.enabled", matchIfMissing = false)
public class DeliveryServiceMock implements DeliveryService {

    @Override
    public LocalDateTime delivery(DeliveryDTO deliveryDTO) {
        return LocalDateTime.now().plusDays(2);
    }

    @Override
    public void cancel(UUID orderId) {

    }
}
