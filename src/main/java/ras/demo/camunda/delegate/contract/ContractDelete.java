package ras.demo.camunda.delegate.contract;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ras.demo.camunda.restService.contractService.ContractService;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ContractDelete implements JavaDelegate {
    private final ContractService contractService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        contractService.delete((UUID) delegateExecution.getVariable("contractId"));
    }
}