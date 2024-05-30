package j3lcardmarket.atelier3.sagaorchestrator.workers;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import j3lcardmarket.atelier3.sagaorchestrator.dto.EditUserCardDto;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BuyProcessWorker {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuyProcessWorker.class.getName());

    @Autowired
    private JobClient jobClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${orchestrator.gateway-url}")
    String gatewayUrl;

    @Value("${orchestrator.token}")
    String orchestratorToken;


    private Map<String, List<String>> orchestratorHeader(){
        return Map.of("Authorization", Collections.singletonList(
                String.format("Bearer %s", orchestratorToken)
        ));
    }

    @ZeebeWorker(type = "buy-1-task")
    public void changeOwner(ActivatedJob job) throws IOException {
        Integer usercardId = (Integer) job.getVariable("usercardId");
        String buyer = (String) job.getVariable("buyer");
        Map<String, Object> ret = new HashMap<>();

        LOGGER.info("CHANGING OWNER");
        URI uri;
        try {
            uri = new URI(gatewayUrl);
            uri = new URI(uri.getScheme(), uri.getHost(),
                    String.format("/api/usercards/%d/editAndReturnPrevious", usercardId),
                    uri.getFragment());
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }

        HttpEntity<EditUserCardDto> request = new HttpEntity<>(
                new EditUserCardDto(buyer, 0),
                new MultiValueMapAdapter<>(orchestratorHeader()));

        ResponseEntity<EditUserCardDto> response;

        try {
            response = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    request,
                    EditUserCardDto.class
            );
        }catch (HttpClientErrorException e){
            throw new IOException(e);
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            EditUserCardDto previousCard = response.getBody();
            ret.put("price", previousCard.getPrice());
            ret.put("previousOwner", previousCard.getOwner());
        } else {
            throw new IOException("Request threw: "+response.getStatusCode());
        }

        jobClient.newCompleteCommand(job.getKey()).variables(ret).send().join();
    }

    @ZeebeWorker(type = "buy-2-task")
    public void registerTransaction(ActivatedJob job) {
        LOGGER.info("REGISTERING TRANSACTION");

        // Compléter la tâche
        jobClient.newCompleteCommand(job.getKey()).send().join();
    }

    @ZeebeWorker(type = "rollback-buy-1-task")
    public void changeBackOwner(ActivatedJob job) {
        LOGGER.info("CHANGING BACK OWNER");

        // Compléter la tâche
        jobClient.newCompleteCommand(job.getKey()).send().join();
    }
}