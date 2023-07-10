package com.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
@Component
public class RestTemplateService {
    private final RestTemplate restTemplate;

    @Autowired
    public RestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getServerData(){

        String url = "http://localhost:8081/server/records";
        String data = restTemplate.getForObject(url,String.class);
        if(!data.isEmpty()){
            return data;
        }else {
            return "";
        }
    }
}
