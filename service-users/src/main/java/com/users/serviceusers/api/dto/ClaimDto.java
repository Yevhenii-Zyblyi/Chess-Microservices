package com.users.serviceusers.api.dto;

public record ClaimDto(long id, String reason, String description, long violator_id) { }
