package ras.demo.camunda.kafka.hadler;


import com.fasterxml.jackson.core.JsonProcessingException;
import ras.demo.camunda.kafka.request.EventSource;

public interface EventHandler<T extends EventSource> {

    boolean canHandle(EventSource eventSource);

    void handleEvent(T eventSource) throws JsonProcessingException;
}
