package ras.demo.camunda.delegate.product;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ras.demo.camunda.model.StartConfirmDTO;
import ras.demo.camunda.restService.productService.ProductService;
import ras.demo.camunda.restService.productService.model.ChangeStatusDTO;
import ras.demo.camunda.restService.productService.model.OrderStatus;

@Component
@RequiredArgsConstructor
public class StatusRejectedDelegate implements JavaDelegate {
    private final ProductService productService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        StartConfirmDTO startConfirmDto = (StartConfirmDTO) delegateExecution.getVariable("orderInfo");
        productService.changeStatus(new ChangeStatusDTO(OrderStatus.REJECTED, null), startConfirmDto.getId());
    }
}
