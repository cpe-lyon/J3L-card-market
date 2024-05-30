package j3lcardmarket.atelier3.sagaorchestrator.controllers;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import j3lcardmarket.atelier3.commons.models.UserInfo;
import j3lcardmarket.atelier3.commons.utils.CardAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orchestrate")
public class OrchestratorController {

    @Autowired
    private ZeebeClient zeebeClient;

    @PostMapping("/buy/{usercardId}")
    @CardAuth
    @SecurityRequirement(name="cardauth")
    public void runProcess(@PathVariable Integer usercardId, @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        zeebeClient
                .newCreateInstanceCommand()
                .bpmnProcessId("buy-process")
                .latestVersion()
                .variables(Map.of("usercardId", usercardId, "buyer", cardUserInfo.userName()))
                .withResult()
                .send().join();
    }
}
