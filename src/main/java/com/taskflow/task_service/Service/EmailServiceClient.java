package com.taskflow.task_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class EmailServiceClient {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CircuitBreakerFactory circuitBreakerFactory;

    private final WebClient webClient;
    @Autowired
    public EmailServiceClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8010/email-service").build();
    }

    public ResponseEntity<String> sendEmail(String toMail , String  subject , String body)
    {
        try {
            CircuitBreaker cb = circuitBreakerFactory.create("Circuit Breaker");
            String url = "http://email-service/email/send?toMail={toMail}&subject={subject}&body={body}";
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = cb.run(() ->restTemplate.exchange(url, HttpMethod.POST,entity,String.class,toMail,subject,body),throwable -> fallbackService());
                 return response;
            //return restTemplate.exchange(url, HttpMethod.POST, entity, String.class, toMail, subject, body);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    private ResponseEntity<String> fallbackService() {
        return ResponseEntity.status(404).body("Invalid Request");
    }


    public Object sentEmailUsingWebClient(String toMail , String subject , String body)
    {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/email/send")
                        .queryParam("toMail",toMail)
                        .queryParam("subject",subject)
                        .queryParam("body",body)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> System.out.println("Email sent successfully: " + response))
                .doOnError(error -> System.err.println("Error occurred: " + error.getMessage()))
                .subscribe();
    }

}
