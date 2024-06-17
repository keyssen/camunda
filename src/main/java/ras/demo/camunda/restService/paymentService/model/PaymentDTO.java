package ras.demo.camunda.restService.paymentService.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentDTO {

    private final UUID orderId;
    private final BigDecimal totalPrice;
    private final String accountNumber;
}
