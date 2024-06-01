package j3lcardmarket.atelier3.sagaorchestrator.utils;

import j3lcardmarket.atelier3.commons.utils.HttpUtils;
import j3lcardmarket.atelier3.sagaorchestrator.dto.EditUserCardDto;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RestHttpUtils extends HttpUtils {

    @Autowired
    RestTemplate restTemplate;


    @Value("${orchestrator.token}")
    String orchestratorToken;

    private Map<String, List<String>> orchestratorHeader(){
        return Map.of("Authorization", Collections.singletonList(
                String.format("Bearer %s", orchestratorToken)
        ));
    }

    public <T,U> T sendRequest(URI uri, HttpMethod method, U body, Class<T> clazz) throws IOException {

        HttpEntity<U> request = new HttpEntity<>(
                body,
                new MultiValueMapAdapter<>(orchestratorHeader()));

        ResponseEntity<T> response;

        try {
            response = restTemplate.exchange(
                    uri,
                    method,
                    request,
                    clazz
            );
        }catch (Exception e){
            throw new IOException(e);
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new IOException("Request threw: "+response.getStatusCode());
        }
    }


    @Value("${orchestrator.gateway-url}")
    String gatewayUrl;
    public URI buildGatewayUri(String path) throws IOException {
        try {
            URI uri = new URI(gatewayUrl);
            return new URI(uri.getScheme(), uri.getHost(),
                    path,
                    uri.getFragment());
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

}
