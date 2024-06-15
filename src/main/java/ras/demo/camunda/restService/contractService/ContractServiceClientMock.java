package ras.demo.camunda.restService.contractService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ras.demo.camunda.restService.contractService.model.ContractRegistrationDTO;

import java.util.UUID;


@Slf4j
@Primary
@Service
@ConditionalOnProperty(name = "rest.contract-service.mock.enabled", matchIfMissing = false)
public class ContractServiceClientMock implements ContractService {

    @Override
    public UUID registration(ContractRegistrationDTO contractRegistrationDTO) {
        return UUID.randomUUID();
    }

    @Override
    public void delete(UUID contractId) {

    }
}