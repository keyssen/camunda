package ras.demo.camunda.restService.productService;

import ras.demo.camunda.restService.productService.model.ChangeStatusDTO;

import java.util.UUID;

public interface ProductService {

    void changeStatus(ChangeStatusDTO changeStatusDTO, UUID orderId);
}