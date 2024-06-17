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

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Compliance implements JavaDelegate {
    private final Producer producer;
    private final DeliveryService deliveryService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws JsonProcessingException {
        StartConfirmDTO startConfirmDto = (StartConfirmDTO) delegateExecution.getVariable("orderInfo");
        producer.sendEvent(Producer.TEST_TOPIC, String.valueOf(startConfirmDto.getInn()), new ComplianceOrderRequest(startConfirmDto.getUserLogin(), startConfirmDto.getAccountNumber(), UUID.fromString(delegateExecution.getBusinessKey())));
    }
}
