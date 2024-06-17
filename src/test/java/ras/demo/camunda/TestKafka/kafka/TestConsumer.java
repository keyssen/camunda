package ras.demo.camunda.TestKafka.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ras.demo.camunda.kafka.hadler.EventHandler;
import ras.demo.camunda.kafka.request.EventSource;

import java.io.IOException;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestConsumer {

    private final Set<EventHandler<EventSource>> eventHandlers;

    @KafkaListener(topics = "test_topic2", containerFactory = "kafkaTestListenerContainerFactoryString")
    public void listenGroupTopic2(byte[] message) {
        log.info("Receive message: {}", message);

        final ObjectMapper objectMapper = new ObjectMapper();

        try {
            final EventSource eventSource = objectMapper.readValue(message, EventSource.class);
            log.info("EventSource: {}", eventSource);

            eventHandlers.stream()
                    .filter(eventSourceEventHandler -> eventSourceEventHandler.canHandle(eventSource))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Handler for eventsource not found"))
                    .handleEvent(eventSource);

        } catch (IOException e) {
            log.error("Couldn't parse message: {}; exception: ", message, e);
        }
    }
}
