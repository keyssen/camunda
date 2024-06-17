package ras.demo.camunda.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ras.demo.camunda.configuration.properties.contractService.ContractServiceProperties;
import ras.demo.camunda.configuration.properties.deliveryService.DeliveryServiceProperties;
import ras.demo.camunda.configuration.properties.notificationService.NotificationServiceProperties;
import ras.demo.camunda.configuration.properties.paymentService.PaymentServiceProperties;
import ras.demo.camunda.configuration.properties.productService.ProductServiceProperties;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
    private final ContractServiceProperties contractServiceProperties;
    private final ProductServiceProperties productServiceProperties;
    private final NotificationServiceProperties notificationServiceProperties;
    private final DeliveryServiceProperties deliveryServiceProperties;
    private final PaymentServiceProperties paymentServiceProperties;

    @Bean
    public WebClient contractServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(contractServiceProperties.getHost()).build();
    }

    @Bean
    public WebClient productServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(productServiceProperties.getHost()).build();
    }

    @Bean
    public WebClient notificationServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(notificationServiceProperties.getHost()).build();
    }

    @Bean
    public WebClient deliveryServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(deliveryServiceProperties.getHost()).build();
    }

    @Bean
    public WebClient paymentServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(paymentServiceProperties.getHost()).build();
    }
}