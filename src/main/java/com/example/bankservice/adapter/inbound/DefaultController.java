package com.example.bankservice.adapter.inbound;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
    @RequestMapping("/")
    public String healthCheck() {
        return "Server is up.";
    }
}
