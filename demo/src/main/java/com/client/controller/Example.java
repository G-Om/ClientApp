package com.client.controller;

import com.client.services.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return restTemplateService.getServerData();
    }

    @GetMapping("/hello")
    public String hello(){return "<h1>Hello World</h1>";}
}
