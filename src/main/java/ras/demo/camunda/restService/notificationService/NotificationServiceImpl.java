package ras.demo.camunda.restService.notificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ras.demo.camunda.configuration.properties.notificationService.NotificationServiceProperties;
import ras.demo.camunda.restService.productService.model.ChangeStatusDTO;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationServiceProperties notificationServiceProperties;

    private final WebClient notificationServiceWebClient;

    @Override
    public void send(UUID orderId, ChangeStatusDTO changeStatusDTO) {
        notificationServiceWebClient.post()
                .uri(String.format("%s/%s", orderId, notificationServiceProperties.getMethods().getSend()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(changeStatusDTO)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}
