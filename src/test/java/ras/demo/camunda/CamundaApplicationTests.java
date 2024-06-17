package ras.demo.camunda;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ras.demo.camunda.configuration.properties.contractService.ContractServiceProperties;
import ras.demo.camunda.configuration.properties.deliveryService.DeliveryServiceProperties;
import ras.demo.camunda.configuration.properties.paymentService.PaymentServiceProperties;
import ras.demo.camunda.configuration.properties.productService.ProductServiceProperties;
import ras.demo.camunda.model.StartConfirmDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.patch;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest()
@EmbeddedKafka(partitions = 1, topics = {"test_topic2", "compliance_to_camunda_topic"}, brokerProperties = {"listeners=PLAINTEXT://localhost:9091", "port=9091"})
@WireMockTest
@DirtiesContext
@Slf4j
class CamundaApplicationTests {

    @RegisterExtension
    static WireMockExtension wireMockServerProduct = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8080))
            .build();

    @RegisterExtension
    static WireMockExtension wireMockServerContract = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8092))
            .build();

    @RegisterExtension
    static WireMockExtension wireMockDeliveryServer = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8094))
            .build();

    @RegisterExtension
    static WireMockExtension wireMockServerPayment = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8095))
            .build();

    @Autowired
    private ProductServiceProperties productServiceProperties;

    @Autowired
    private ContractServiceProperties contractServiceProperties;

    @Autowired
    private DeliveryServiceProperties deliveryServiceProperties;

    @Autowired
    private PaymentServiceProperties paymentServiceProperties;

    @Autowired
    private RuntimeService runtimeService;

    private ProcessEngine processEngine;

    @Autowired
    private HistoryService historyService;

    private static final String PROCESS_KEY = "ConfirmProcessKey";

    private static final Random random = new Random();

    private static final int accountNumberLength = 8;

    private static final int innLength = 10;

    private Map<String, Object> variables;

    @BeforeEach
    void setUp() {
        processEngine = ProcessEngines.getDefaultProcessEngine();

        wireMockServerProduct.stubFor(patch(urlEqualTo(String.format("/orders/52b37abb-9138-4c63-a085-f6ed586cf2c5/%s", productServiceProperties.getMethods().getChangeStatus())))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)));

        wireMockServerContract.stubFor(post(urlEqualTo("/" + contractServiceProperties.getMethods().getRegistration()))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)
                        .withBody(UUID.randomUUID().toString())));

        wireMockDeliveryServer.stubFor(post(urlEqualTo("/" + deliveryServiceProperties.getMethods().getGetDate()))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(LocalDateTime.now().plusDays(2).toString())));

        wireMockServerPayment.stubFor(post(urlEqualTo("/" + paymentServiceProperties.getMethods().getPayment()))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("FAILED")));
    }

    private static String generateRandomInn() {
        StringBuilder code = new StringBuilder(innLength);
        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(innLength);
            code.append(digit);
        }
        return code.toString();
    }

    private static String generateRandomAccountNumber() {
        StringBuilder code = new StringBuilder(accountNumberLength);
        for (int i = 0; i < accountNumberLength; i++) {
            int digit = random.nextInt(10);
            code.append(digit);
        }
        return code.toString();
    }

    @Test
    public void testWaitForEndEvent() {
        StartConfirmDTO startConfirmDTO = new StartConfirmDTO(UUID.fromString("52b37abb-9138-4c63-a085-f6ed586cf2c5"), "string", generateRandomInn(), generateRandomAccountNumber(), BigDecimal.valueOf(40.00), "login-null");
        variables.put("orderInfo", startConfirmDTO);

        ProcessInstance processInstance = runtimeService
                .createProcessInstanceByKey(PROCESS_KEY)
                .setVariables(variables)
                .businessKey(UUID.randomUUID().toString())
                .execute();

        waitUntilEndEventReached(processInstance.getId(), "Event_0jyrtva");

        List<HistoricActivityInstance> endEvents = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstance.getId())
                .activityId("Event_0jyrtva")
                .finished()
                .list();

        List<HistoricActivityInstance> errorEndEvents = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstance.getId())
                .activityId("Event_1xhimd0")
                .finished()
                .list();

        log.info("errorEndEvents: " + errorEndEvents.toString());
        assertNotNull(endEvents);
        assert (!endEvents.isEmpty());
        assert (errorEndEvents.isEmpty());
    }

    private void waitUntilEndEventReached(String processInstanceId, String endEventId) {
        boolean isReached = false;
        while (!isReached) {
            List<HistoricActivityInstance> endEvents = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .activityId(endEventId)
                    .finished()
                    .list();

            if (endEvents != null && !endEvents.isEmpty()) {
                isReached = true;
            } else {
                try {
                    Thread.sleep(1000); // Wait for 1 second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
