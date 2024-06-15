package ras.demo.camunda.kafka.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "event"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ComplianceOrderRequest.class, name = "COMPLIANCE_ORDER"),
        @JsonSubTypes.Type(value = ComplianceRequest.class, name = "COMPLIANCE"),
})
public interface EventSource {

    Event getEvent();
}
