package com.taskflow.task_service.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class TaskConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate()
    {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
    @Bean
    public WebClient.Builder getWebClient()
    {
        return WebClient.builder();
    }
}
