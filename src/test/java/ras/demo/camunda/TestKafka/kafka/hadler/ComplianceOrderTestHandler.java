package ras.demo.camunda.TestKafka.kafka.hadler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ras.demo.camunda.TestKafka.kafka.TestProducer;
import ras.demo.camunda.kafka.hadler.EventHandler;
import ras.demo.camunda.kafka.request.ComplianceOrderRequest;
import ras.demo.camunda.kafka.request.ComplianceRequest;
import ras.demo.camunda.kafka.request.Event;
import ras.demo.camunda.kafka.request.EventSource;

@Slf4j
@Component
@RequiredArgsConstructor
public class ComplianceOrderTestHandler implements EventHandler<ComplianceOrderRequest> {

    private final TestProducer producer;

    @Override
    public boolean canHandle(final EventSource eventSource) {
        return Event.COMPLIANCE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public void handleEvent(ComplianceOrderRequest eventSource) throws JsonProcessingException {
        producer.sendEvent(TestProducer.PRODUCER_TOPIC_NAME, String.valueOf(eventSource.getBusinessKey()), new ComplianceRequest(true, eventSource.getBusinessKey()));

    }
}
