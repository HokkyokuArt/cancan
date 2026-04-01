package com.articos.cancan.domain.auth;

import com.articos.cancan.domain.usuario.*;
import com.articos.cancan.security.jwt.role.*;
import jakarta.validation.constraints.*;

import java.util.*;

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
            String token,
            Role role,
            UUID id,
            String nome
    ) {

        public TokenResponse(String token, Usuario usuario) {
            this(token, usuario.getRole(), usuario.getId(), usuario.getNome());
        }
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
