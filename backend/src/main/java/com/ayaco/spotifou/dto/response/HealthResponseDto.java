package com.ayaco.spotifou.dto.response;

public record HealthResponseDto(String status) {
    public HealthResponseDto() {
        this("ok");
    }
}
