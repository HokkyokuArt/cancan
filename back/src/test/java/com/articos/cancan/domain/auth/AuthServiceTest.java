package com.articos.cancan.domain.auth;

import com.articos.cancan.common.exceptions.core.*;
import com.articos.cancan.domain.auth.AuthRecords.*;
import com.articos.cancan.domain.usuario.*;
import com.articos.cancan.security.jwt.*;
import com.articos.cancan.utils.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.security.authentication.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    public void login_sucesso() {
        LoginPayload payload = new LoginPayload("teste@email.com", "123456");

        Usuario usuario = new Usuario();
        ReflectionUtils.setIn(usuario, "id", UUID.randomUUID());
        usuario.setNome("Artico");
        usuario.setEmail("teste@email.com");

        when(usuarioRepository.findByEmail(payload.email())).thenReturn(Optional.of(usuario));
        when(jwtService.generateToken(usuario.getEmail())).thenReturn("token-jwt");

        TokenResponse response = authService.login(payload);

        assertThat(response).isNotNull();
        assertThat(response.token()).isEqualTo("token-jwt");
        assertThat(response.id()).isEqualTo(usuario.getId());
        assertThat(response.nome()).isEqualTo(usuario.getNome());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioRepository).findByEmail(payload.email());
        verify(jwtService).generateToken(usuario.getEmail());
    }

    @Test
    public void login_entidadeNaoEncontrada() {
        LoginPayload payload = new LoginPayload("teste@email.com", "123456");
        when(usuarioRepository.findByEmail(payload.email())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authService.login(payload))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("Usuário");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioRepository).findByEmail(payload.email());
        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    public void login_email_ou_senha_errado() {
        LoginPayload payload = new LoginPayload("teste@email.com", "senha-invalida");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        assertThatThrownBy(() -> authService.login(payload))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessageContaining("Credenciais inválidas");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioRepository, never()).findByEmail(anyString());
        verify(jwtService, never()).generateToken(anyString());
    }
}