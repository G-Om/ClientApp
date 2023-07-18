package com.client.controller;

import com.client.services.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class Example {

    private final RestTemplateService restTemplateService;

    @Autowired
    public Example(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    @GetMapping("/records")
    public String getData(){
        String data = restTemplateService.getServerData();
        return data;
    }

    @GetMapping("/hello")
    public String hello(){return "<h1>Hello World I am a Client Application</h1>";}
}
