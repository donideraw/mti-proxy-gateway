package com.mti.proxygateway.controller;

import com.mti.proxygateway.client.ClientService;
import com.mti.proxygateway.utils.ConverterUtil;
import com.mti.proxygateway.utils.UriUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class DefaultController {

    @Value("${header-removed}")
    private String headerRemoved;
    @Value("${is.json-body}")
    public boolean isJsonBody;

    private final ClientService service;

    public DefaultController(ClientService service) {
        this.service = service;
    }

    @RequestMapping("/**")
    public ResponseEntity<?> passThrough(@RequestBody(required = false) String body,
                                         final HttpServletRequest request) {

        String path = UriUtil.getPath(new String(request.getRequestURL()));
        HttpHeaders headers = getHeader(request);

        String response = "";

        switch (request.getMethod()) {
            case "POST":
                response = service.post(headers, path, ConverterUtil.convertToJson(body));
                break;
            case "PUT":
                response = service.put(headers, path, ConverterUtil.convertToJson(body));
                break;
            case "GET":
                response = service.get(headers, path);
                break;
            case "OPTIONS":
                response = service.options(headers, path);
                break;
        }

        return ResponseEntity.ok(ConverterUtil.convertToJson(response));
    }

    public HttpHeaders getHeader(HttpServletRequest request) {
        /* get all headers from request */
        HttpHeaders httpHeaders = Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        h -> Collections.list(request.getHeaders(h)),
                        (oldValue, newValue) -> newValue,
                        HttpHeaders::new
                ));

        /* remove unnecessary header */
        String[] headerRemovedList = headerRemoved.split(",");
        for (int i = 0; i< headerRemovedList.length; i++) {
            httpHeaders.remove(headerRemovedList[i]);
        }

        /* force change header */
        if (isJsonBody) {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        }

        return httpHeaders;
    }

}
