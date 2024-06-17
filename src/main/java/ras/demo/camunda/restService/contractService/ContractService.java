package ras.demo.camunda.restService.contractService;

import ras.demo.camunda.restService.contractService.model.ContractRegistrationDTO;

import java.util.UUID;

public interface ContractService {

    UUID registration(ContractRegistrationDTO contractRegistrationDTO);

    void delete(UUID contractId);
}