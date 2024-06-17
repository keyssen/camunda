package ras.demo.camunda.configuration.properties.productService;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ras.demo.camunda.configuration.properties.model.MockProperties;
import ras.demo.camunda.configuration.properties.productService.model.ProductMethodsProperties;

@Configuration
@ConfigurationProperties(prefix = "rest.product-service")
@Getter
@Setter
public class ProductServiceProperties {

    private MockProperties mock;

    private String host;

    private ProductMethodsProperties methods;
}