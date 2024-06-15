package ras.demo.camunda.delegate;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReturnDelegate implements JavaDelegate {
    private final RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        runtimeService
                .createProcessInstanceModification(delegateExecution.getProcessInstanceId())
                .startAfterActivity("Activity_07hgdtp")
                .execute();
    }
}
