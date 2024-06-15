package ras.demo.camunda.configuration.properties.paymentService;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ras.demo.camunda.configuration.properties.model.MockProperties;
import ras.demo.camunda.configuration.properties.paymentService.model.PaymentMethodsProperties;

@Configuration
@ConfigurationProperties(prefix = "rest.payment-service")
@Getter
@Setter
public class PaymentServiceProperties {

    private MockProperties mock;

    private String host;

    private PaymentMethodsProperties methods;
}