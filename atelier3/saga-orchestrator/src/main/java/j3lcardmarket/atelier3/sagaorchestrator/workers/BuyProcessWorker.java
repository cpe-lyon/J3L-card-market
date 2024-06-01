package j3lcardmarket.atelier3.sagaorchestrator.workers;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import j3lcardmarket.atelier3.sagaorchestrator.dto.CreateTransactionDTO;
import j3lcardmarket.atelier3.sagaorchestrator.dto.EditUserCardDto;
import j3lcardmarket.atelier3.sagaorchestrator.utils.RestHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class BuyProcessWorker {


    @Autowired
    private JobClient jobClient;

    @Autowired
    private RestHttpUtils restHttpUtils;



    private EditUserCardDto editAndReturnPrevious(Integer id, String newOwner, Integer price, boolean force) throws IOException {
        URI uri = restHttpUtils.buildGatewayUri(String.format("/api/usercards/%d/editAndReturnPrevious%s", id, force ? "?force=true":""));

        return restHttpUtils.sendRequest(
                uri,
                HttpMethod.POST,
                new EditUserCardDto(newOwner, price),
                EditUserCardDto.class
        );
    }

    @ZeebeWorker(type = "buy-1-task")
    public void changeOwner(ActivatedJob job) {
        try{
            Integer usercardId = (Integer) job.getVariable("usercardId");
            String buyer = (String) job.getVariable("buyer");

            EditUserCardDto previous = editAndReturnPrevious(usercardId, buyer, 0, false);

            Map<String, Object> ret = new HashMap<>();
            ret.put("price", previous.getPrice());
            ret.put("previousOwner", previous.getOwner());

            jobClient.newCompleteCommand(job.getKey()).variables(ret).send();
        }catch (IOException exception){
            jobClient.newThrowErrorCommand(job.getKey()).errorCode(exception.getMessage()).send();
        }
    }

    @ZeebeWorker(type = "buy-2-task")
    public void registerTransaction(ActivatedJob job){
        try{
            URI uri = restHttpUtils.buildGatewayUri("/api/transactions");

            Integer usercardId = (Integer) job.getVariable("usercardId");
            String buyer = (String) job.getVariable("buyer");
            Integer price = (Integer) job.getVariable("price");
            String seller = (String) job.getVariable("previousOwner");


            restHttpUtils.sendRequest(
                    uri,
                    HttpMethod.POST,
                    new CreateTransactionDTO(usercardId, seller, buyer, price),
                    Object.class
            );
            jobClient.newCompleteCommand(job.getKey()).send();
        }catch (IOException exception){
            jobClient.newThrowErrorCommand(job.getKey()).errorCode(exception.getMessage()).send();
        }
    }

    @ZeebeWorker(type = "rollback-buy-1-task")
    public void changeBackOwner(ActivatedJob job) {
        try{
            Integer usercardId = (Integer) job.getVariable("usercardId");
            Integer price = (Integer) job.getVariable("price");
            String seller = (String) job.getVariable("previousOwner");

            editAndReturnPrevious(usercardId, seller, price, true);

            jobClient.newCompleteCommand(job.getKey()).send();
        }catch (IOException exception){
            jobClient.newThrowErrorCommand(job.getKey()).errorCode(exception.getMessage()).send();
        }
    }
}
