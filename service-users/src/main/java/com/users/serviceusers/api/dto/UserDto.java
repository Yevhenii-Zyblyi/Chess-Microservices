package com.users.serviceusers.api.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public record UserDto( String username,
                       String password,
                       String first_name,
                       String last_name,
                       Long rating,
                       String country,
                       Long received_claims,
                       String email) {

}
