package com.photoai.apigateway.integration;

import com.photoai.apigateway.dto.ErrorResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiGatewayIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private final ErrorResponseDTO expectedError = new ErrorResponseDTO(
        "SERVICE_UNAVAILABLE", 
        "O serviço solicitado está indisponível no momento. Tente novamente mais tarde."
    );

    @Test
    @DisplayName("Deve acionar o fallback genérico para a rota /api/v1/generate/**")
    void testGatewayRouteFallback_Generation() {
        webTestClient
            .get().uri("/api/v1/generate/test") 
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.SERVICE_UNAVAILABLE)
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(ErrorResponseDTO.class)
                .isEqualTo(expectedError); 
    }

    @Test
    @DisplayName("Deve acionar o fallback genérico para a rota /api/v1/auth/**")
    void testGatewayRouteFallback_Identity() {
        webTestClient
            .get().uri("/api/v1/auth/test") 
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.SERVICE_UNAVAILABLE)
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(ErrorResponseDTO.class)
                .isEqualTo(expectedError); 
    }

    @Test
    @DisplayName("Deve retornar o DTO de erro genérico ao chamar o endpoint de fallback diretamente")
    void testFallbackControllerDirectly() {
        webTestClient
            .get().uri("/fallback/service-unavailable") 
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.SERVICE_UNAVAILABLE)
            .expectBody(ErrorResponseDTO.class)
                .isEqualTo(expectedError);
    }
}