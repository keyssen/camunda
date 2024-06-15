package ras.demo.camunda.delegate;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ras.demo.camunda.model.StartConfirmDTO;
import ras.demo.camunda.restService.paymentService.PaymentService;
import ras.demo.camunda.restService.paymentService.model.PaymentDTO;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PaymentDelegate implements JavaDelegate {
    private final PaymentService paymentService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        StartConfirmDTO startConfirmDto = (StartConfirmDTO) delegateExecution.getVariable("orderInfo");
        System.out.println("PaymentDelegate");
        System.out.println(paymentService.payment(new PaymentDTO(startConfirmDto.getId(), startConfirmDto.getTotalPrice(), startConfirmDto.getAccountNumber())));
        if (Objects.equals(paymentService.payment(new PaymentDTO(startConfirmDto.getId(), startConfirmDto.getTotalPrice(), startConfirmDto.getAccountNumber())), "FAILED")) {
            throw new BpmnError("delegateError");
        }
        delegateExecution.setVariable("error", false);
    }
}
