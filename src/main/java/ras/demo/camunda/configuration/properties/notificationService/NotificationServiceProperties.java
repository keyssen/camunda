package ras.demo.camunda.configuration.properties.notificationService;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ras.demo.camunda.configuration.properties.model.MockProperties;
import ras.demo.camunda.configuration.properties.notificationService.model.NotificationMethodsProperties;

@Configuration
@ConfigurationProperties(prefix = "rest.notification-service")
@Getter
@Setter
public class NotificationServiceProperties {

    private MockProperties mock;

    private String host;

    private NotificationMethodsProperties methods;
}