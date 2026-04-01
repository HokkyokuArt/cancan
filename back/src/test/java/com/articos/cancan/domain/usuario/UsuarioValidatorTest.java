package com.articos.cancan.domain.usuario;

import com.articos.cancan.common.exceptions.core.*;
import com.articos.cancan.domain.usuario.dto.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioValidatorTest {

    @Mock
    private UsuarioRepository repository;

    private UsuarioValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UsuarioValidator(repository);
    }

    @Test
    void validateCreate_NAO_quebra_quando_emailNaoExiste() {
        Usuario usuario = mock(Usuario.class);
        UsuarioPayloadDTO dto = mock(UsuarioPayloadDTO.class);

        when(dto.getEmail()).thenReturn("artico@email.com");
        when(repository.existsByEmail("artico@email.com")).thenReturn(false);

        assertThatCode(() -> validator.validateCreate(usuario, dto))
                .doesNotThrowAnyException();
    }

    @Test
    void validateCreate_quebra_quando_emailJaExiste() {
        Usuario usuario = mock(Usuario.class);
        UsuarioPayloadDTO dto = mock(UsuarioPayloadDTO.class);

        when(dto.getEmail()).thenReturn("artico@email.com");
        when(repository.existsByEmail("artico@email.com")).thenReturn(true);

        assertThatThrownBy(() -> validator.validateCreate(usuario, dto))
                .isInstanceOf(DuplicidadeException.class)
                .hasMessage("Email já cadastrado");
    }

    @Test
    void validateUpdate_NAO_quebra_quando_naoTrocouEmail() {
        Usuario usuarioAntigo = mock(Usuario.class);
        UsuarioPayloadDTO dto = mock(UsuarioPayloadDTO.class);

        when(usuarioAntigo.getEmail()).thenReturn("artico@email.com");
        when(dto.getEmail()).thenReturn("artico@email.com");

        assertThatCode(() -> validator.validateUpdate(usuarioAntigo, dto))
                .doesNotThrowAnyException();

        verify(repository, never()).existsByEmail(any());
    }

    @Test
    void validateUpdate_NAO_quebra_quando_trocouEmailMasNovoEmailNaoExiste() {
        Usuario usuarioAntigo = mock(Usuario.class);
        UsuarioPayloadDTO dto = mock(UsuarioPayloadDTO.class);

        when(usuarioAntigo.getEmail()).thenReturn("velho@email.com");
        when(dto.getEmail()).thenReturn("novo@email.com");
        when(repository.existsByEmail("novo@email.com")).thenReturn(false);

        assertThatCode(() -> validator.validateUpdate(usuarioAntigo, dto))
                .doesNotThrowAnyException();
    }

    @Test
    void validateUpdate_quebra_quando_trocouEmailENovoEmailJaExiste() {
        Usuario usuarioAntigo = mock(Usuario.class);
        UsuarioPayloadDTO dto = mock(UsuarioPayloadDTO.class);

        when(usuarioAntigo.getEmail()).thenReturn("velho@email.com");
        when(dto.getEmail()).thenReturn("novo@email.com");
        when(repository.existsByEmail("novo@email.com")).thenReturn(true);

        assertThatThrownBy(() -> validator.validateUpdate(usuarioAntigo, dto))
                .isInstanceOf(DuplicidadeException.class)
                .hasMessage("Email já cadastrado");
    }
}