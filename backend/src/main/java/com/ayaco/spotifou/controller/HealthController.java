package com.ayaco.spotifou.controller;

import com.ayaco.spotifou.dto.response.HealthResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/api/v1/health")
    public HealthResponseDto health() {
        return new HealthResponseDto();
    }
}
