package ras.demo.camunda.restService.paymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ras.demo.camunda.configuration.properties.paymentService.PaymentServiceProperties;
import ras.demo.camunda.restService.paymentService.model.PaymentDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentServiceProperties paymentServiceProperties;

    private final WebClient paymentServiceWebClient;

    public String payment(PaymentDTO paymentDTO) {
        return paymentServiceWebClient.post()
                .uri(String.format(paymentServiceProperties.getMethods().getPayment()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(paymentDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
