package ras.demo.camunda.restService.paymentService;

import ras.demo.camunda.restService.paymentService.model.PaymentDTO;

public interface PaymentService {

    String payment(PaymentDTO paymentDTO);
}