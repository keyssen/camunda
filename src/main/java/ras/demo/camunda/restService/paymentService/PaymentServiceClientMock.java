package ras.demo.camunda.restService.paymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ras.demo.camunda.restService.paymentService.model.PaymentDTO;


@Slf4j
@Primary
@Service
@ConditionalOnProperty(name = "rest.payment-service.mock.enabled", matchIfMissing = false)
public class PaymentServiceClientMock implements PaymentService {

    @Override
    public String payment(PaymentDTO paymentDTO) {
//        return new Random().nextBoolean();
        return "SUCCESS";
//        return "FAILED";
    }
}