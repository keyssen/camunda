package ras.demo.camunda.restService.deliveryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ras.demo.camunda.configuration.properties.deliveryService.DeliveryServiceProperties;
import ras.demo.camunda.restService.deliveryService.model.DeliveryDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryServiceProperties deliveryServiceProperties;

    private final WebClient deliveryServiceWebClient;

    @Override
    public LocalDateTime delivery(DeliveryDTO deliveryDTO) {
        return this.deliveryServiceWebClient.post()
                .uri(deliveryServiceProperties.getMethods().getGetDate())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(deliveryDTO)
                .retrieve()
                .bodyToMono(LocalDateTime.class)
                .block();
    }

    @Override
    public void cancel(UUID orderId) {
        this.deliveryServiceWebClient.post()
                .uri(String.format("%s/%s", deliveryServiceProperties.getMethods().getCancel(), orderId))
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}
