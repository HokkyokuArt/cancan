package com.articos.cancan.domain.auth;

import com.articos.cancan.domain.auth.AuthRecords.*;
import com.articos.cancan.domain.usuario.*;
import com.articos.cancan.security.jwt.role.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Operações relacionadas a autenticação")
public class AuthRestController {
    private final AuthService authService;

    @Operation(
            summary = "Registrar usuário",
            description = "Cria um novo usuário no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário registrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de retorno",
                                    value = "\"550e8400-e29b-41d4-a716-446655440000\""
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Sem permissão para acessar o recurso")
    })
    @AdminOnly
    @PostMapping("/register")
    public ResponseEntity<UUID> register(
            @RequestBody(
                    required = true,
                    description = "Dados necessários para registrar um novo usuário",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Exemplo de payload",
                                    value = """
                                            {
                                              "nome": "João da Silva",
                                              "email": "joao@email.com",
                                              "senha": "123456"
                                            }
                                            """
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody @Valid RegisterPayload request
    ) {
        Usuario user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user.getId());
    }

    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuário e retorna os tokens de acesso.",
            security = {}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de retorno",
                                    value = """
                                            {
                                              "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
                                              "role": "ROLE_AMIN",
                                              "id": "56e867e3-ddd0-433d-8dad-eb43a162d127",
                                              "nome": "JOÃO ZÉ RUELA",
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/logar")
    public ResponseEntity<TokenResponse> logar(
            @RequestBody(
                    required = true,
                    description = "Credenciais do usuário para autenticação",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Exemplo de payload",
                                    value = """
                                            {
                                              "email": "joao@email.com",
                                              "senha": "123456"
                                            }
                                            """
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody @Valid LoginPayload request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
}