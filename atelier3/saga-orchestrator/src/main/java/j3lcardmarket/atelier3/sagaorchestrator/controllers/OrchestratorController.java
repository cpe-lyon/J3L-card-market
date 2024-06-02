package j3lcardmarket.atelier3.sagaorchestrator.controllers;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.ZeebeFuture;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import j3lcardmarket.atelier3.commons.models.UserInfo;
import j3lcardmarket.atelier3.commons.utils.CardAuth;
import j3lcardmarket.atelier3.commons.utils.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/orchestrate")
public class OrchestratorController {

    @Autowired
    private ZeebeClient zeebeClient;

    @PostMapping("/buy/{usercardId}")
    @CardAuth
    @ResponseBody
    @SecurityRequirement(name="cardauth")
    public String runBuyProcess(@PathVariable Integer usercardId, @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        ProcessInstanceResult evt = zeebeClient
                .newCreateInstanceCommand()
                .bpmnProcessId("buy-process")
                .latestVersion()
                .variables(Map.of("usercardId", usercardId, "buyer", cardUserInfo.userName()))
                .withResult()
                .send().join();
        Object error = evt.getVariablesAsMap().get("error");
        if (error != null) throw new RuntimeException(error.toString());
        return "OK";
    }

    @Value("${auth.token}")
    String authToken;

    @PostMapping("/register")
    @ResponseBody
    @SecurityRequirement(name="cardauth")
    public String runCreateUserProcess(@RequestParam("user") String user, @RequestHeader("Authorization") String authHeader) throws IOException, ExecutionException, InterruptedException {
        if(!authHeader.startsWith("Bearer") || !authHeader.substring("Bearer".length()).trim().equals(authToken.trim()))
            throw new ForbiddenException();
        ProcessInstanceResult evt = zeebeClient
                .newCreateInstanceCommand()
                .bpmnProcessId("createuser-process")
                .latestVersion()
                .variables(Map.of("userid", user))
                .withResult()
                .requestTimeout(Duration.ofSeconds(30))
                .send().join();
        Object error = evt.getVariablesAsMap().get("error");
        if (error != null) throw new RuntimeException(error.toString());
        return "OK";
    }
}
