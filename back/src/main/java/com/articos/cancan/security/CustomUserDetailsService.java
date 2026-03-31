package com.articos.cancan.security;

import com.articos.cancan.common.exceptions.core.*;
import com.articos.cancan.domain.usuario.*;
import lombok.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(Usuario.class, "validar token"));
        return new User(
                usuario.getId().toString(),
                usuario.getSenha(),
                List.of(new SimpleGrantedAuthority(usuario.getRole().name()))
        );
    }
}
