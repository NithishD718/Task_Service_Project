package com.taskflow.task_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserClient {
@Autowired
    RestTemplate restTemplate;

    public String getUserEmail(Integer userId) {
        String url ="http://localhost:8010/user-service/user/get/{userId}";
       return restTemplate.getForObject(url,String.class,userId);
    }
}
