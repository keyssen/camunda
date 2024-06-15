package ras.demo.camunda.restService.contractService.model;

import lombok.Data;

@Data
public class ContractRegistrationDTO {
    private final String inn;

    private final String accountNumber;
}
