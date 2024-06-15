package ras.demo.camunda.delegate.product;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ras.demo.camunda.model.StartConfirmDTO;
import ras.demo.camunda.restService.productService.ProductService;
import ras.demo.camunda.restService.productService.model.ChangeStatusDTO;
import ras.demo.camunda.restService.productService.model.OrderStatus;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class FinalStatusDelegate implements JavaDelegate {
    private final ProductService productService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("FinalStatusDelegate");
        Boolean errorFlag = (Boolean) delegateExecution.getVariable("error");
        LocalDateTime deliveryDateTime = (LocalDateTime) delegateExecution.getVariable("deliveryDateTime");
        StartConfirmDTO startConfirmDto = (StartConfirmDTO) delegateExecution.getVariable("orderInfo");
        if (errorFlag) {
            productService.changeStatus(new ChangeStatusDTO(OrderStatus.CANCELLED, null), startConfirmDto.getId());
        } else {
            productService.changeStatus(new ChangeStatusDTO(OrderStatus.CONFIRMED, deliveryDateTime), startConfirmDto.getId());
        }
    }
}
