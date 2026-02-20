package com.ayaco.spotifou.controller;

import com.ayaco.spotifou.config.CorsConfig;
import com.ayaco.spotifou.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HealthController.class)
@Import({CorsConfig.class, SecurityConfig.class})
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // AC 2 & 3 : GET /api/v1/health → 200, {"status":"ok"}
    @Test
    void health_returns200WithOkBody() throws Exception {
        mockMvc.perform(get("/api/v1/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"));
    }

    // AC 1 & 4 : preflight depuis http://localhost:5173 autorisé
    @Test
    void health_allowsCorsPreflightFromFrontendOrigin() throws Exception {
        mockMvc.perform(options("/api/v1/health")
                        .header(HttpHeaders.ORIGIN, "http://localhost:5173")
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:5173"));
    }

    // AC 1 (négatif) : origine inconnue bloquée
    @Test
    void health_blocksCorsFromUnknownOrigin() throws Exception {
        mockMvc.perform(options("/api/v1/health")
                        .header(HttpHeaders.ORIGIN, "http://evil.com")
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET"))
                .andExpect(header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
    }

    // Prérequis Epic 2 : allowCredentials=true pour les cookies httpOnly OAuth
    @Test
    void health_corsAllowsCredentialsForFrontendOrigin() throws Exception {
        mockMvc.perform(options("/api/v1/health")
                        .header(HttpHeaders.ORIGIN, "http://localhost:5173")
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET"))
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true"));
    }
}
