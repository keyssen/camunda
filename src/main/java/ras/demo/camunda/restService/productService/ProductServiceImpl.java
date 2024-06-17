package ras.demo.camunda.restService.productService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ras.demo.camunda.configuration.properties.productService.ProductServiceProperties;
import ras.demo.camunda.restService.productService.model.ChangeStatusDTO;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductServiceProperties productServiceProperties;

    private final WebClient productServiceWebClient;

    @Override
    public void changeStatus(ChangeStatusDTO changeStatusDTO, UUID orderId) {
        productServiceWebClient.patch()
                .uri(String.format("orders/%s/%s", orderId, productServiceProperties.getMethods().getChangeStatus()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(changeStatusDTO)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}
