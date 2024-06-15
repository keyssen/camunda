//package ras.demo.camunda;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import org.camunda.bpm.engine.RuntimeService;
//import org.camunda.bpm.engine.delegate.DelegateExecution;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ras.demo.camunda.delegate.Compliance;
//import ras.demo.camunda.kafka.Producer;
//import ras.demo.camunda.model.StartConfirmDTO;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//import java.util.UUID;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class CamundaApplicationTests1 {
//    @Autowired
//    private RuntimeService runtimeService;
//
//    @Autowired
//    private Producer producer;
//
//    private static final String PROCESS_KEY = "ConfirmProcessKey";
//
//    private static final Random random = new Random();
//
//    private static final int accountNumberLength = 8;
//
//    private static final int innLength = 10;
//
//    private static String generateRandomInn() {
//        StringBuilder code = new StringBuilder(innLength);
//        for (int i = 0; i < 10; i++) {
//            int digit = random.nextInt(innLength);
//            code.append(digit);
//        }
//        return code.toString();
//    }
//
//    private static String generateRandomAccountNumber() {
//        StringBuilder code = new StringBuilder(accountNumberLength);
//        for (int i = 0; i < accountNumberLength; i++) {
//            int digit = random.nextInt(10);
//            code.append(digit);
//        }
//        return code.toString();
//    }
//
//    Map<String, Object> variables;
//
//    @Test
//    void contextLoads() {
//        variables = new HashMap<>();
//        StartConfirmDTO startConfirmDTO = new StartConfirmDTO(UUID.fromString("52b37abb-9138-4c63-a085-f6ed586cf2c5"), "string", generateRandomInn(), generateRandomAccountNumber(), BigDecimal.valueOf(40.00), "login-null");
//        variables.put("orderInfo", startConfirmDTO);
//        runtimeService
//                .createProcessInstanceByKey(PROCESS_KEY)
//                .setVariable("orderInfo", startConfirmDTO)
//                .businessKey(UUID.randomUUID().toString())
//                .execute();
//    }
//
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
//
//}
