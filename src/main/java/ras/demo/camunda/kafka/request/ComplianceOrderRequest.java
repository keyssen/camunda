package ras.demo.camunda.kafka.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceOrderRequest implements EventSource {

    private String login;

    private String inn;

    private UUID businessKey;

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Event getEvent() {
        return Event.COMPLIANCE_ORDER;
    }
}
