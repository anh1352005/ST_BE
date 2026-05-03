package com.sieuthi.st_be.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Spring Boot đang chạy! ✅";
    }

    @GetMapping("/test")
    public String test() {
        return "API test hoạt động! 🚀";
    }
}