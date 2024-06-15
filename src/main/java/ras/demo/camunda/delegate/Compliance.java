package ras.demo.camunda.delegate;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ras.demo.camunda.kafka.Producer;
import ras.demo.camunda.kafka.request.ComplianceOrderRequest;
import ras.demo.camunda.model.StartConfirmDTO;
import ras.demo.camunda.restService.deliveryService.DeliveryService;
import ras.demo.camunda.restService.deliveryService.model.DeliveryDTO;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Compliance implements JavaDelegate {
    private final Producer producer;
    private final DeliveryService deliveryService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws JsonProcessingException {
        DeliveryDTO deliveryDTO = new DeliveryDTO("sdsasdad", UUID.randomUUID());
        StartConfirmDTO startConfirmDto = (StartConfirmDTO) delegateExecution.getVariable("orderInfo");
        System.out.println("orderInfo");
        System.out.println(deliveryService.delivery(deliveryDTO));
        producer.sendEvent(Producer.TEST_TOPIC, String.valueOf(startConfirmDto.getInn()), new ComplianceOrderRequest(startConfirmDto.getUserLogin(), startConfirmDto.getAccountNumber(), UUID.fromString(delegateExecution.getBusinessKey())));
        System.out.println(startConfirmDto);
        System.out.println("orderInfoEnd");
    }
}
