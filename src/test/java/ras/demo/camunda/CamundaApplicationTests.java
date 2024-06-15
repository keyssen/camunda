package ras.demo.camunda;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.camunda.bpm.engine.RuntimeService;
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
import ras.demo.camunda.kafka.Consumer;
import ras.demo.camunda.kafka.Producer;
import ras.demo.camunda.model.StartConfirmDTO;
import ras.demo.camunda.restService.deliveryService.DeliveryService;
import ras.demo.camunda.restService.deliveryService.model.DeliveryDTO;
import ras.demo.camunda.restService.paymentService.PaymentService;
import ras.demo.camunda.restService.paymentService.model.PaymentDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@RunWith(SpringRunner.class)
@SpringBootTest()
@EmbeddedKafka(brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@WireMockTest
@DirtiesContext
class CamundaApplicationTests {

    @Autowired
    private Consumer consumer;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8095))
            .build();

    @RegisterExtension
    static WireMockExtension wireMockDeliveryServer = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8094))
            .build();

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private DeliveryService deliveryService;

    @BeforeEach
    void setUp() {
        wireMockServer.stubFor(post(urlEqualTo("/payment"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("FAILED")));

        wireMockDeliveryServer.stubFor(post(urlEqualTo("/getdate"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("2024-06-13T19:58:00.644607")));
    }

    @Test
    void testMakePayment() {
        PaymentDTO paymentDTO = new PaymentDTO(
                UUID.randomUUID(),
                new BigDecimal("40.00"),
                "12345678"
        );

        String response = paymentService.payment(paymentDTO);
        System.out.println(response);
        System.out.println(response);
    }

    @Test
    void testDelivery() {
        PaymentDTO paymentDTO = new PaymentDTO(
                UUID.randomUUID(),
                new BigDecimal("40.00"),
                "12345678"
        );

        DeliveryDTO deliveryDTO = new DeliveryDTO("sdsasdad", UUID.randomUUID());
        LocalDateTime response = deliveryService.delivery(deliveryDTO);
        System.out.println(response);
        System.out.println(response);
    }

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private Producer producer;

    private static final String PROCESS_KEY = "ConfirmProcessKey";

    private static final Random random = new Random();

    private static final int accountNumberLength = 8;

    private static final int innLength = 10;

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

    Map<String, Object> variables;

    @Test
    void contextLoads() {
        variables = new HashMap<>();
        StartConfirmDTO startConfirmDTO = new StartConfirmDTO(UUID.fromString("52b37abb-9138-4c63-a085-f6ed586cf2c5"), "string", generateRandomInn(), generateRandomAccountNumber(), BigDecimal.valueOf(40.00), "login-null");
        variables.put("orderInfo", startConfirmDTO);
        runtimeService
                .createProcessInstanceByKey(PROCESS_KEY)
                .setVariable("orderInfo", startConfirmDTO)
                .businessKey(UUID.randomUUID().toString())
                .execute();
    }

//    @Test
//    void testComplianceDelegate() throws JsonProcessingException {
//        // Создайте мок DelegateExecution
//        DelegateExecution execution = Mockito.mock(DelegateExecution.class);
//
//        // Установите поведение для мока
//        when(execution.getVariable("orderInfo")).thenReturn(variables.get("orderInfo"));
//        when(execution.getBusinessKey()).thenReturn(variables.get("orderInfo"));
//
//        // Создайте экземпляр делегата и вызовите его метод execute
//        Compliance delegate = new Compliance(producer);
//        delegate.execute(execution);
//
//        // Проверьте, что переменная "result" установлена правильно
//        verify(execution).getBusinessKey();
//    }
}
