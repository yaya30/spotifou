package com.ayaco.spotifou.controller;

import com.ayaco.spotifou.dto.response.HealthResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/api/v1/test")
    public HealthResponseDto testCors (@RequestParam(required = false, defaultValue = "World") String name) {
        return new HealthResponseDto(String.format("Test CORS succed. Value: %s", name));
    }
}