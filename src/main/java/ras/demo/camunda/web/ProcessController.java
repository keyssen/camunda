package ras.demo.camunda.web;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ras.demo.camunda.model.StartConfirmDTO;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProcessController {

    private final RuntimeService runtimeService;

    private static final String PROCESS_KEY = "ConfirmProcessKey";

    @PostMapping("confirm/start")
    public String startConfirm(@RequestBody StartConfirmDTO startConfirmDTO) {

        return runtimeService
                .createProcessInstanceByKey(PROCESS_KEY)
                .setVariable("orderInfo", startConfirmDTO)
                .businessKey(UUID.randomUUID().toString())
                .execute()
                .getBusinessKey();
    }
}

