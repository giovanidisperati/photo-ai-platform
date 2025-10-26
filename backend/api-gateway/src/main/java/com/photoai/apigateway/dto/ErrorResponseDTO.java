package com.photoai.apigateway.dto;

/**
 * Default DTO for error responses across the platform.
 * A Java 'record' is ideal for this, as it represents an immutable DTO.
 *
 * @param errorCode A standardized error code (e.g., "SERVICE_UNAVAILABLE").
 * @param message A user-readable message.
 */
public record ErrorResponseDTO(String errorCode, String message) {}