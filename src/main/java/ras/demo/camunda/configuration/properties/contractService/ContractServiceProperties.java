package ras.demo.camunda.configuration.properties.contractService;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ras.demo.camunda.configuration.properties.contractService.model.ContractMethodsProperties;
import ras.demo.camunda.configuration.properties.model.MockProperties;

/**
 * Класс для конфигурации свойств приложения, связанных с веб-сервисом для работы с аккаунтами.
 * Свойства задаются в файле application.yaml с префиксом "rest.account-service".
 */
@Configuration
@ConfigurationProperties(prefix = "rest.contract-service")
@Getter
@Setter
public class ContractServiceProperties {
    
    private MockProperties mock;

    private String host;

    private ContractMethodsProperties methods;
}