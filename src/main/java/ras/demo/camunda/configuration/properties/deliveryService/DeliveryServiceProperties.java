package ras.demo.camunda.configuration.properties.deliveryService;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ras.demo.camunda.configuration.properties.deliveryService.model.DeliveryMethodsProperties;
import ras.demo.camunda.configuration.properties.model.MockProperties;

@Configuration
@ConfigurationProperties(prefix = "rest.delivery-service")
@Getter
@Setter
public class DeliveryServiceProperties {

    private MockProperties mock;

    private String host;

    private DeliveryMethodsProperties methods;
}