package ras.demo.camunda.restService.contractService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ras.demo.camunda.configuration.properties.contractService.ContractServiceProperties;
import ras.demo.camunda.restService.contractService.model.ContractRegistrationDTO;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractServiceProperties contractServiceProperties;

    private final WebClient contractServiceWebClient;

    public UUID registration(ContractRegistrationDTO contractRegistrationDTO) {
        log.info("Impl contract service is used");
        return contractServiceWebClient.post()
                .uri(contractServiceProperties.getMethods().getRegistration())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(contractRegistrationDTO)
                .retrieve()
                .bodyToMono(UUID.class)
                .block();
    }

    @Override
    public void delete(UUID contractId) {
        contractServiceWebClient.delete()
                .uri(String.format("%s/%s", contractServiceProperties.getMethods().getDelete(), contractId))
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}
