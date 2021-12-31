package com.games.servicegames.api.dto;

public record GameDto(long whiteId, long blackId, String status, String result, long duration, String variant, String timeControl) {
}
