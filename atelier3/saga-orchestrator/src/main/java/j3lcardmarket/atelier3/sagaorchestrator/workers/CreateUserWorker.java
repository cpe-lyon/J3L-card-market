package j3lcardmarket.atelier3.sagaorchestrator.workers;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import j3lcardmarket.atelier3.sagaorchestrator.dto.EditUserCardDto;
import j3lcardmarket.atelier3.sagaorchestrator.utils.RestHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CreateUserWorker {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateUserWorker.class.getName());

    @Autowired
    private JobClient jobClient;


    @Autowired
    private RestHttpUtils restHttpUtils;


    @Value("${orchestrator.gateway-url}")
    String gatewayUrl;


    private URI getUsersUri(ActivatedJob job) throws IOException {
        String userId = job.getVariable("userid").toString();
        LOGGER.info("REQUEST FOR USER "+userId);
        return restHttpUtils.buildGatewayUri(String.format("/api/users/%s", userId));
    }

    @ZeebeWorker(type = "createuser-2-task")
    public void createUser(ActivatedJob job) throws IOException {
        URI uri = getUsersUri(job);

        restHttpUtils.sendRequest(uri, HttpMethod.POST, null, Object.class);
        jobClient.newCompleteCommand(job.getKey()).send();
    }

    @ZeebeWorker(type = "createuser-2-task-rollback")
    public void deleteUser(ActivatedJob job) throws IOException {
        URI uri = getUsersUri(job);
        restHttpUtils.sendRequest(uri, HttpMethod.DELETE, null, Object.class);
        jobClient.newCompleteCommand(job.getKey()).send();
    }

    @ZeebeWorker(type = "createuser-3-task")
    public void giveCards(ActivatedJob job) throws IOException {
        LOGGER.info("REGISTERING TRANSACTION");
        String userId = job.getVariable("userid").toString();
        String rawCardIds = job.getVariable("cardIds").toString();

        List<Integer> cardIds = Arrays.stream(rawCardIds.split(";")).map(Integer::parseInt).collect(Collectors.toList());
        if(cardIds.size() != 5) throw new IOException("cardIds variable must contain 5 ids");

        URI uri = restHttpUtils.buildGatewayUri(String.format("/api/usercards/starter/%s", userId));

        restHttpUtils.sendRequest(uri, HttpMethod.POST, cardIds, Object.class);

        // Compléter la tâche
        jobClient.newCompleteCommand(job.getKey()).send();
    }

    @ZeebeWorker(type = "createuser-1-task")
    public void getFiveCards(ActivatedJob job) throws IOException {
        LOGGER.info("CHANGING BACK OWNER");

        URI uri = restHttpUtils.buildGatewayUri("/api/cards/pickStarterCards");

        //No need for IOException if failling because we count the ids anyway
        //No need for header auth
        String ids = restHttpUtils.httpRequest(uri.toString());
        if(ids.split(";").length != 5) throw new IOException("Need 5 cards");

        // Compléter la tâche
        jobClient.newCompleteCommand(job.getKey()).variable("cardIds", ids).send();
    }
}
