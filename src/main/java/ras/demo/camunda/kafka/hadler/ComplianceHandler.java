package ras.demo.camunda.kafka.hadler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Component;
import ras.demo.camunda.kafka.request.ComplianceRequest;
import ras.demo.camunda.kafka.request.Event;
import ras.demo.camunda.kafka.request.EventSource;

@Slf4j
@Component
@RequiredArgsConstructor
public class ComplianceHandler implements EventHandler<ComplianceRequest> {

    private final RuntimeService runtimeService;

    @Override
    public boolean canHandle(final EventSource eventSource) {
        return Event.COMPLIANCE.equals(eventSource.getEvent());
    }

    @Override
    public void handleEvent(ComplianceRequest eventSource) {
        log.info("ComplianceHandler");
        log.info(String.valueOf(!eventSource.getIsCompliance()));

        runtimeService
                .createMessageCorrelation("continueProcessMsg")
                .processInstanceBusinessKey(eventSource.getBusinessKey().toString())
                .setVariable("errorCompliance", !eventSource.getIsCompliance())
                .correlate();

    }
}
