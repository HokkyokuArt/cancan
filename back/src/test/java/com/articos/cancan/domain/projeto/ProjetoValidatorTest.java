package com.articos.cancan.domain.projeto;

import com.articos.cancan.common.exceptions.projeto.*;
import com.articos.cancan.domain.projeto.dto.*;
import com.articos.cancan.domain.usuario.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetoValidatorTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private ProjetoValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ProjetoValidator(usuarioRepository);
    }

    @Test
    void validateCreate_NAO_quebra_quando_donoForAdmin() {
        Projeto projeto = mock(Projeto.class);
        ProjetoPayloadDTO dto = mock(ProjetoPayloadDTO.class);
        UUID dono = UUID.randomUUID();

        when(dto.getDono()).thenReturn(dono);
        when(usuarioRepository.isAdmin(dono)).thenReturn(true);

        assertThatCode(() -> validator.validateCreate(projeto, dto))
                .doesNotThrowAnyException();
    }

    @Test
    void validateCreate_quebra_quando_donoNaoForAdmin() {
        Projeto projeto = mock(Projeto.class);
        ProjetoPayloadDTO dto = mock(ProjetoPayloadDTO.class);
        UUID dono = UUID.randomUUID();

        when(dto.getDono()).thenReturn(dono);
        when(usuarioRepository.isAdmin(dono)).thenReturn(false);

        assertThatThrownBy(() -> validator.validateCreate(projeto, dto))
                .isInstanceOf(DonoNaoPossuiRoleAdminException.class);
    }

    @Test
    void validateUpdate_NAO_quebra_quando_donoForAdmin() {
        Projeto projetoAntigo = mock(Projeto.class);
        ProjetoPayloadDTO dto = mock(ProjetoPayloadDTO.class);
        UUID dono = UUID.randomUUID();

        when(dto.getDono()).thenReturn(dono);
        when(usuarioRepository.isAdmin(dono)).thenReturn(true);

        assertThatCode(() -> validator.validateUpdate(projetoAntigo, dto))
                .doesNotThrowAnyException();
    }

    @Test
    void validateUpdate_quebra_quando_donoNaoForAdmin() {
        Projeto projetoAntigo = mock(Projeto.class);
        ProjetoPayloadDTO dto = mock(ProjetoPayloadDTO.class);
        UUID dono = UUID.randomUUID();

        when(dto.getDono()).thenReturn(dono);
        when(usuarioRepository.isAdmin(dono)).thenReturn(false);

        assertThatThrownBy(() -> validator.validateUpdate(projetoAntigo, dto))
                .isInstanceOf(DonoNaoPossuiRoleAdminException.class);
    }
}