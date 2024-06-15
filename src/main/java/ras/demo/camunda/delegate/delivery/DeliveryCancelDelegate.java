package ras.demo.camunda.delegate.delivery;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ras.demo.camunda.model.StartConfirmDTO;
import ras.demo.camunda.restService.deliveryService.DeliveryService;

@Component
@RequiredArgsConstructor
public class DeliveryCancelDelegate implements JavaDelegate {
    private final DeliveryService deliveryService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        StartConfirmDTO startConfirmDto = (StartConfirmDTO) delegateExecution.getVariable("orderInfo");
        deliveryService.cancel(startConfirmDto.getId());
    }
}
