package com.eatthis.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthCheck")
public class HealthCheckController {

    @GetMapping
    public String healthCheck() {
        return "Health Check OK!!";
    }

}