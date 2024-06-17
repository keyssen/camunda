package ras.demo.camunda.kafka;

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
public class Producer {
    private final KafkaTemplate<String, byte[]> kafkaTemplateByteArray;

    public static final String TEST_TOPIC = "test_topic2";

    public void sendEvent(final String topic, final String key, final EventSource event) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final byte[] data = objectMapper.writeValueAsBytes(event);

        kafkaTemplateByteArray.send(topic, key, data);
    }
}
