package ras.demo.camunda.TestKafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ras.demo.camunda.kafka.hadler.EventHandler;
import ras.demo.camunda.kafka.request.EventSource;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class EventHandlerTestConfig {

    @Bean
    <T extends EventSource> Set<EventHandler<T>> eventTestHandlers(Set<EventHandler<T>> eventHandlers) {
        return new HashSet<>(eventHandlers);
    }
}
