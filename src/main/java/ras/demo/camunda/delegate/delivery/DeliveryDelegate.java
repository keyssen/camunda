package ras.demo.camunda.delegate.delivery;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ras.demo.camunda.model.StartConfirmDTO;
import ras.demo.camunda.restService.deliveryService.DeliveryService;
import ras.demo.camunda.restService.deliveryService.model.DeliveryDTO;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DeliveryDelegate implements JavaDelegate {
    private final DeliveryService deliveryService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            StartConfirmDTO startConfirmDto = (StartConfirmDTO) delegateExecution.getVariable("orderInfo");
            LocalDateTime deliveryDateTime = deliveryService.delivery(new DeliveryDTO(startConfirmDto.getDeliveryAddress(), startConfirmDto.getId()));
            System.out.println("DeliveryDelegate");
            delegateExecution.setVariable("deliveryDateTime", deliveryDateTime);
        } catch (Exception e) {
            throw new BpmnError("delegateError");
        }

    }
}
