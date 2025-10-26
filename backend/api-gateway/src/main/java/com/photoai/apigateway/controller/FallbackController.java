package com.photoai.apigateway.controller;

import com.photoai.apigateway.dto.ErrorResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * REST controller that exposes fallback endpoints used by the API Gateway's
 * circuit breaker or routing filters.
 *
 * When a downstream service is unreachable or unhealthy, these endpoints provide a
 * stable, descriptive error response so clients receive a controlled failure
 * instead of a raw exception or timeout.
 *
 * Responsibilities:
 * - Expose endpoints consumed by circuit breaker or routing logic when a target
 * service is down.
 * - Return consistent JSON error payloads with appropriate HTTP status codes.
 *
 */

@RestController
public class FallbackController {

    /**
     * Fallback endpoint invoked when the generation-service is unavailable.
     *
     * Returns a 503 (Service Unavailable) response with a JSON body containing
     * a machine-readable error code and a user-friendly message to inform callers
     * that the service is temporarily unavailable.
     *
     * Response details:
     * - HTTP status: 503 Service Unavailable
     * - Content-Type: application/json
     * - Body: ErrorResponseDTO with fields such as an error code and a human-readable message
     *
     * Intended usage:
     * This endpoint should be called by the API Gateway's circuit breaker or
     * route-fallback mechanism when the downstream service cannot
     * be reached or fails health checks.
     *
     * @return a Mono wrapping a ResponseEntity containing an ErrorResponseDTO with
     *         details about the service unavailability
     */
    @GetMapping("/fallback/service-unavailable")
    public Mono<ResponseEntity<ErrorResponseDTO>> genericServiceFallback() {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "SERVICE_UNAVAILABLE",
                "O serviço solicitado está indisponível no momento. Tente novamente mais tarde."
        );

        return Mono.just(
                ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(errorResponse));
    }
}