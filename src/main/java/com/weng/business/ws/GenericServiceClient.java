package com.weng.business.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GenericServiceClient implements WebClientFactory {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public WebClient.Builder createWebClientBuilder() {
        return webClientBuilder;
    }

    // Method to handle all HTTP requests
    private <T, R> Mono<ResponseEntity<T>> sendRequest(HttpMethod method, String baseUrl, String uri, R body, Class<T> responseType, Object... uriVariables) {
        // Create a webclient by using WebClientFactory.createWebClient with baseUrl
        WebClient webclient = createWebClient(baseUrl);

        WebClient.RequestBodyUriSpec bodySpec = webclient.method(method);
        WebClient.RequestHeadersSpec<?> requestSpec;

        // Only methods that can have a body (POST, PUT) should use bodyValue
        if (method == HttpMethod.POST || method == HttpMethod.PUT) {
            requestSpec = bodySpec.uri(uri, uriVariables).bodyValue(body);
        } else {
            requestSpec = bodySpec.uri(uri, uriVariables);
        }

        return requestSpec
                .retrieve()
                .toEntity(responseType)
                .onErrorResume(e -> {
                    log.error("Error: {}", e.getMessage());
                    return Mono.empty();  // TODO: Handle Error
                });
    }

    // Specific methods for each HTTP method (GET, POST, PUT, DELETE)

    public <T> Mono<ResponseEntity<T>> get(String baseUrl, String uri, Class<T> responseType, Object... uriVariables) {
        return sendRequest(HttpMethod.GET, baseUrl, uri, null, responseType, uriVariables);
    }

    public <T, R> Mono<ResponseEntity<T>> post(String baseUrl, String uri, R body, Class<T> responseType, Object... uriVariables) {
        return sendRequest(HttpMethod.POST, baseUrl, uri, body, responseType, uriVariables);
    }

    public <T, R> Mono<ResponseEntity<T>> put(String baseUrl, String uri, R body, Class<T> responseType, Object... uriVariables) {
        return sendRequest(HttpMethod.PUT, baseUrl, uri, body, responseType, uriVariables);
    }

    public Mono<ResponseEntity<Void>> delete(String baseUrl, String uri, Object... uriVariables) {
        return sendRequest(HttpMethod.DELETE, baseUrl, uri, null, Void.class, uriVariables);
    }
}