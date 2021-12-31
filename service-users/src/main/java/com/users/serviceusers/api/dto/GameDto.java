package com.users.serviceusers.api.dto;

public record GameDto(long id, long whiteId, long blackId,
                      String status, String result, long duration, String variant, String timeControl) {
}
