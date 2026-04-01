package com.articos.cancan.domain.auth;

import com.articos.cancan.common.exceptions.core.*;
import com.articos.cancan.domain.auth.AuthRecords.*;
import com.articos.cancan.domain.usuario.*;
import com.articos.cancan.security.jwt.*;
import lombok.*;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public TokenResponse login(LoginPayload request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.senha()
                )
        );

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(request.email());
        if (usuarioOpt.isEmpty()) {
            throw new EntidadeNaoEncontradaException(Usuario.class, "logar");
        }

        Usuario usuario = usuarioOpt.get();
        String token = jwtService.generateToken(usuario.getEmail());
        return new TokenResponse(token, usuario);
    }
}
