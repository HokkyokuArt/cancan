package com.articos.cancan.domain.auth;

import jakarta.validation.constraints.*;

public class AuthRecords {
    public record LoginPayload(
            @NotBlank
            @Email
            String email,

            @NotBlank
            String senha
    ) {
    }

    public record TokenResponse(
            String token
    ) {
    }

    public record RegisterPayload(
            @NotBlank
            @Size(max = 100)
            String nome,

            @NotBlank
            @Email
            @Size(max = 150)

            String email,

            @NotBlank
            @Size(min = 6, max = 100)
            String senha
    ) {
    }
}
