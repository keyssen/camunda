package ras.demo.camunda.TestKafka.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ras.demo.camunda.kafka.request.EventSource;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestProducer {
    private final KafkaTemplate<String, byte[]> kafkaTestTemplateByteArray;

    public static final String PRODUCER_TOPIC_NAME = "compliance_to_camunda_topic";

    public void sendEvent(final String topic, final String key, final EventSource event) throws JsonProcessingException {
        
        final ObjectMapper objectMapper = new ObjectMapper();
        final byte[] data = objectMapper.writeValueAsBytes(event);

        kafkaTestTemplateByteArray.send(topic, key, data);
    }
}
