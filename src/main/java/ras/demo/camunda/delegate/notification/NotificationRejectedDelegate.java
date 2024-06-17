package ras.demo.camunda.delegate.notification;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ras.demo.camunda.model.StartConfirmDTO;
import ras.demo.camunda.restService.notificationService.NotificationService;
import ras.demo.camunda.restService.productService.model.ChangeStatusDTO;
import ras.demo.camunda.restService.productService.model.OrderStatus;

@Component
@RequiredArgsConstructor
public class NotificationRejectedDelegate implements JavaDelegate {
    private final NotificationService notificationService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        StartConfirmDTO startConfirmDto = (StartConfirmDTO) delegateExecution.getVariable("orderInfo");
        notificationService.send(startConfirmDto.getId(), new ChangeStatusDTO(OrderStatus.REJECTED, null));
    }
}
