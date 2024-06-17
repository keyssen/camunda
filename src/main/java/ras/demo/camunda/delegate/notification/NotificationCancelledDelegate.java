package ras.demo.camunda.delegate.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ras.demo.camunda.model.StartConfirmDTO;
import ras.demo.camunda.restService.notificationService.NotificationService;
import ras.demo.camunda.restService.productService.model.ChangeStatusDTO;
import ras.demo.camunda.restService.productService.model.OrderStatus;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationCancelledDelegate implements JavaDelegate {
    private final NotificationService notificationService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        log.info("NotificationCancelledDelegate");
        StartConfirmDTO startConfirmDto = (StartConfirmDTO) delegateExecution.getVariable("orderInfo");
        notificationService.send(startConfirmDto.getId(), new ChangeStatusDTO(OrderStatus.CANCELLED, null));
    }
}
