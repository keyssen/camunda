package ras.demo.camunda.delegate.contract;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ras.demo.camunda.model.StartConfirmDTO;
import ras.demo.camunda.restService.contractService.ContractService;
import ras.demo.camunda.restService.contractService.model.ContractRegistrationDTO;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ContractRegistration implements JavaDelegate {
    private final ContractService contractService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            StartConfirmDTO startConfirmDto = (StartConfirmDTO) delegateExecution.getVariable("orderInfo");
            UUID contractId = contractService.registration(new ContractRegistrationDTO(startConfirmDto.getInn(), startConfirmDto.getAccountNumber()));
            delegateExecution.setVariable("contractId", contractId);
        } catch (Exception e) {
            throw new BpmnError("delegateError");
        }
    }
}