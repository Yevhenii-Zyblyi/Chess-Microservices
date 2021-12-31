package com.claims.serviceclaims.api.dro;

public record ClaimDto(String reason, String description, long violatorId) {
}
