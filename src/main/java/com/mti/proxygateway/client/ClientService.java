package com.mti.proxygateway.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ClientService {

    @Value("${base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public ClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String post(HttpHeaders headers, String path, Map<String, Object> body) {
        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(
                    RequestEntity.post(baseUrl + path)
                            .headers(headers)
                            .body(body), String.class);
        } catch (HttpStatusCodeException e) {
            response = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return "";

    }

    public String put(HttpHeaders headers, String path, Map<String, Object> body) {
        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(
                RequestEntity.put(baseUrl + path)
                        .headers(headers)
                        .body(body), String.class);
        } catch (HttpStatusCodeException e) {
            response = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return "";
    }

    public String get(HttpHeaders headers, String path) {
        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(
                RequestEntity.get(baseUrl + path)
                        .headers(headers)
                        .build(), String.class);
        } catch (HttpStatusCodeException e) {
            response = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return "";
    }

    public String options(HttpHeaders headers, String path) {
        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(
                RequestEntity.options(baseUrl + path)
                        .headers(headers)
                        .build(), String.class);

        } catch (HttpStatusCodeException e) {
            response = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return "";
    }

}
