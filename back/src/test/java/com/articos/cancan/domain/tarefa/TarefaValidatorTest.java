package com.articos.cancan.domain.tarefa;

import com.articos.cancan.common.exceptions.tarefa.*;
import com.articos.cancan.domain.auth.*;
import com.articos.cancan.domain.projeto.*;
import com.articos.cancan.domain.tarefa.dto.*;
import com.articos.cancan.domain.tarefa.prioridadetarefa.*;
import com.articos.cancan.domain.tarefa.statustarefa.*;
import com.articos.cancan.domain.usuario.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaValidatorTest {

    @Mock
    private TarefaRepository repository;

    @Mock
    private UsuarioRepository usuarioRepository;

    private TarefaValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new TarefaValidator(repository, usuarioRepository);
    }

    @Test
    public void validateResponsavelNaoEstaNoProjeto_NAO_Quebra_quando_usuarioLogadoDono() {
        UUID usuarioAtual = UUID.randomUUID();
        Usuario dono = mock(Usuario.class);
        when(dono.getId()).thenReturn(usuarioAtual);
        Projeto projeto = mock(Projeto.class);
        when(projeto.getDono()).thenReturn(dono);
        Tarefa tarefa = mock(Tarefa.class);
        when(tarefa.getProjeto()).thenReturn(projeto);
        try (MockedStatic<AuthContext> authContextMock = mockStatic(AuthContext.class)) {
            authContextMock.when(AuthContext::getUsuarioAtual).thenReturn(usuarioAtual);
            assertThatCode(() -> TarefaValidator.validateResponsavelNaoEstaNoProjeto(tarefa))
                    .doesNotThrowAnyException();
        }
    }

    @Test
    public void validateResponsavelNaoEstaNoProjeto_NAO_Quebra_quando_ResponsavelTaNoProjeto() {
        UUID usuarioAtual = UUID.randomUUID();
        UUID donoProjeto = UUID.randomUUID();

        Usuario dono = mock(Usuario.class);
        when(dono.getId()).thenReturn(donoProjeto);

        Usuario responsavel = mock(Usuario.class);

        Projeto projeto = mock(Projeto.class);
        when(projeto.getDono()).thenReturn(dono);
        when(projeto.getMembros()).thenReturn(Set.of(responsavel));

        Tarefa tarefa = mock(Tarefa.class);
        when(tarefa.getProjeto()).thenReturn(projeto);
        when(tarefa.getResponsavel()).thenReturn(responsavel);

        try (MockedStatic<AuthContext> authContextMock = mockStatic(AuthContext.class)) {
            authContextMock.when(AuthContext::getUsuarioAtual).thenReturn(usuarioAtual);

            assertThatCode(() -> TarefaValidator.validateResponsavelNaoEstaNoProjeto(tarefa))
                    .doesNotThrowAnyException();
        }
    }

    @Test
    public void validateResponsavelNaoEstaNoProjeto_quebra_quando_ResponsavelNAOTaNoProjeto() {
        UUID usuarioAtual = UUID.randomUUID();
        UUID donoProjeto = UUID.randomUUID();

        Usuario dono = mock(Usuario.class);
        when(dono.getId()).thenReturn(donoProjeto);

        Usuario responsavel = mock(Usuario.class);
        Usuario outroMembro = mock(Usuario.class);

        Projeto projeto = mock(Projeto.class);
        when(projeto.getDono()).thenReturn(dono);
        when(projeto.getMembros()).thenReturn(Set.of(outroMembro));

        Tarefa tarefa = mock(Tarefa.class);
        when(tarefa.getProjeto()).thenReturn(projeto);
        when(tarefa.getResponsavel()).thenReturn(responsavel);

        try (MockedStatic<AuthContext> authContextMock = mockStatic(AuthContext.class)) {
            authContextMock.when(AuthContext::getUsuarioAtual).thenReturn(usuarioAtual);

            assertThatThrownBy(() -> TarefaValidator.validateResponsavelNaoEstaNoProjeto(tarefa))
                    .isInstanceOf(ResponsavelNaoPertenceAoProjetoException.class);
        }
    }

    @Test
    public void validateMoverTarefa_NAO_Quebra_quando_tarefaNaoEstaDone() {
        Tarefa tarefa = mock(Tarefa.class);
        StatusTarefa statusAtual = mock(StatusTarefa.class);

        when(tarefa.getStatus()).thenReturn(statusAtual);
        when(statusAtual.isDone()).thenReturn(false);

        assertThatCode(() -> TarefaValidator.validateMoverTarefa(tarefa, mock(TarefaPayloadDTO.class)))
                .doesNotThrowAnyException();
    }

    @Test
    public void validateMoverTarefa_NAO_Quebra_quando_saiDeDonePraInProgress() {
        Tarefa tarefa = mock(Tarefa.class);
        TarefaPayloadDTO dto = mock(TarefaPayloadDTO.class);
        StatusTarefa statusAtual = mock(StatusTarefa.class);
        StatusTarefa novoStatus = mock(StatusTarefa.class);

        when(tarefa.getStatus()).thenReturn(statusAtual);
        when(dto.getStatus()).thenReturn(novoStatus);
        when(statusAtual.isDone()).thenReturn(true);
        when(novoStatus.isInProgress()).thenReturn(true);

        assertThatCode(() -> TarefaValidator.validateMoverTarefa(tarefa, dto))
                .doesNotThrowAnyException();
    }

    @Test
    public void validateMoverTarefa_quebra_quando_saiDeDonePraOutroStatusDiferenteDeInProgress() {
        Tarefa tarefa = mock(Tarefa.class);
        TarefaPayloadDTO dto = mock(TarefaPayloadDTO.class);
        StatusTarefa statusAtual = mock(StatusTarefa.class);
        StatusTarefa novoStatus = mock(StatusTarefa.class);

        when(tarefa.getStatus()).thenReturn(statusAtual);
        when(dto.getStatus()).thenReturn(novoStatus);
        when(statusAtual.isDone()).thenReturn(true);
        when(novoStatus.isInProgress()).thenReturn(false);

        assertThatThrownBy(() -> TarefaValidator.validateMoverTarefa(tarefa, dto))
                .isInstanceOf(MoverTarefaException.class);
    }

    @Test
    public void validateFecharTarefaPrioridadeCritical_quebra_quando_naoForAdmin() {
        UUID usuarioAtual = UUID.randomUUID();

        PrioridadeTarefa prioridade = mock(PrioridadeTarefa.class);
        when(prioridade.isCritical()).thenReturn(true);

        StatusTarefa novoStatus = mock(StatusTarefa.class);
        when(novoStatus.isDone()).thenReturn(true);

        Tarefa tarefa = mock(Tarefa.class);
        when(tarefa.getPrioridade()).thenReturn(prioridade);

        TarefaPayloadDTO dto = mock(TarefaPayloadDTO.class);
        when(dto.getStatus()).thenReturn(novoStatus);

        try (MockedStatic<AuthContext> authContextMock = mockStatic(AuthContext.class)) {
            authContextMock.when(AuthContext::getUsuarioAtual).thenReturn(usuarioAtual);
            when(usuarioRepository.isAdmin(usuarioAtual)).thenReturn(false);

            assertThatThrownBy(() -> validator.validateFecharTarefaPrioridadeCritical(tarefa, dto))
                    .isInstanceOf(FecharTarefaException.class);
        }
    }

    @Test
    public void validateFecharTarefaPrioridadeCritical_NAO_quebra_quando_forAdmin() {
        UUID usuarioAtual = UUID.randomUUID();

        PrioridadeTarefa prioridade = mock(PrioridadeTarefa.class);
        when(prioridade.isCritical()).thenReturn(true);

        StatusTarefa status = mock(StatusTarefa.class);
        when(status.isDone()).thenReturn(true);

        Tarefa tarefa = mock(Tarefa.class);
        when(tarefa.getPrioridade()).thenReturn(prioridade);

        TarefaPayloadDTO dto = mock(TarefaPayloadDTO.class);
        when(dto.getStatus()).thenReturn(status);

        try (MockedStatic<AuthContext> authContextMock = mockStatic(AuthContext.class)) {
            authContextMock.when(AuthContext::getUsuarioAtual).thenReturn(usuarioAtual);
            when(usuarioRepository.isAdmin(usuarioAtual)).thenReturn(true);

            assertThatCode(() -> validator.validateFecharTarefaPrioridadeCritical(tarefa, dto))
                    .doesNotThrowAnyException();
        }
    }

    @Test
    public void validateFecharTarefaPrioridadeCritical_NAO_quebra_quando_NAO_forCritical() {
        UUID usuarioAtual = UUID.randomUUID();

        PrioridadeTarefa prioridade = mock(PrioridadeTarefa.class);
        when(prioridade.isCritical()).thenReturn(false);

        StatusTarefa novoStatus = mock(StatusTarefa.class);
        when(novoStatus.isDone()).thenReturn(true);

        Tarefa tarefa = mock(Tarefa.class);
        when(tarefa.getPrioridade()).thenReturn(prioridade);

        TarefaPayloadDTO dto = mock(TarefaPayloadDTO.class);
        when(dto.getStatus()).thenReturn(novoStatus);

        try (MockedStatic<AuthContext> authContextMock = mockStatic(AuthContext.class)) {
            authContextMock.when(AuthContext::getUsuarioAtual).thenReturn(usuarioAtual);
            when(usuarioRepository.isAdmin(usuarioAtual)).thenReturn(false);

            assertThatCode(() -> validator.validateFecharTarefaPrioridadeCritical(tarefa, dto))
                    .doesNotThrowAnyException();
        }
    }

    @Test
    public void validateAtingiuMaximoTarefasInProgress_quebra_quando_QuantidadeForMaiorOuIgualAoLimite() {
        Usuario responsavel = mock(Usuario.class);
        UUID responsavelId = UUID.randomUUID();
        when(responsavel.getId()).thenReturn(responsavelId);

        StatusTarefa status = mock(StatusTarefa.class);
        when(status.isInProgress()).thenReturn(true);

        Tarefa tarefa = mock(Tarefa.class);
        when(tarefa.getResponsavel()).thenReturn(responsavel);

        TarefaPayloadDTO dto = mock(TarefaPayloadDTO.class);
        when(dto.getStatus()).thenReturn(status);

        when(repository.countByResponsavelIdAndStatus(responsavelId, status)).thenReturn(5L);

        assertThatThrownBy(() -> validator.validateAtingiuMaximoTarefasInProgress(tarefa, dto))
                .isInstanceOf(AtingiuLimiteTarefasException.class);
    }

    @Test
    public void validateAtingiuMaximoTarefasInProgress_NAO_quebra_quando_QuantidadeForMenorOuIgualAoLimite() {
        Usuario responsavel = mock(Usuario.class);
        UUID responsavelId = UUID.randomUUID();
        when(responsavel.getId()).thenReturn(responsavelId);

        StatusTarefa status = mock(StatusTarefa.class);
        when(status.isInProgress()).thenReturn(true);

        Tarefa tarefa = mock(Tarefa.class);
        when(tarefa.getResponsavel()).thenReturn(responsavel);

        TarefaPayloadDTO dto = mock(TarefaPayloadDTO.class);
        when(dto.getStatus()).thenReturn(status);

        when(repository.countByResponsavelIdAndStatus(responsavelId, status)).thenReturn(4L);

        assertThatCode(() -> validator.validateAtingiuMaximoTarefasInProgress(tarefa, dto))
                .doesNotThrowAnyException();
    }

    @Test
    public void validateAtingiuMaximoTarefasInProgress_NAO_quebra_quando_NovoStatusNaoForInProgress() {
        Usuario responsavel = mock(Usuario.class);

        StatusTarefa status = mock(StatusTarefa.class);
        when(status.isInProgress()).thenReturn(false);

        Tarefa tarefa = mock(Tarefa.class);
        when(tarefa.getResponsavel()).thenReturn(responsavel);

        TarefaPayloadDTO dto = mock(TarefaPayloadDTO.class);
        when(dto.getStatus()).thenReturn(status);

        assertThatCode(() -> validator.validateAtingiuMaximoTarefasInProgress(tarefa, dto))
                .doesNotThrowAnyException();

        verify(repository, never()).countByResponsavelIdAndStatus(any(), any());
    }

    @Test
    public void validateView_NAO_quebra_quando_usuarioForAdmin() {
        UUID usuarioAtual = UUID.randomUUID();

        Tarefa tarefa = mock(Tarefa.class);

        try (MockedStatic<AuthContext> authContextMock = mockStatic(AuthContext.class)) {
            authContextMock.when(AuthContext::getUsuarioAtual).thenReturn(usuarioAtual);
            when(usuarioRepository.isAdmin(usuarioAtual)).thenReturn(true);

            assertThatCode(() -> validator.validateView(tarefa))
                    .doesNotThrowAnyException();
        }
    }

    @Test
    public void validateView_NAO_quebra_quando_usuarioPertencerAoProjetoDaTarefa() {
        UUID usuarioAtual = UUID.randomUUID();

        Usuario usuario = mock(Usuario.class);
        when(usuario.getId()).thenReturn(usuarioAtual);

        Projeto projeto = mock(Projeto.class);
        when(projeto.getMembros()).thenReturn(Set.of(usuario));

        Tarefa tarefa = mock(Tarefa.class);
        when(tarefa.getProjeto()).thenReturn(projeto);

        try (MockedStatic<AuthContext> authContextMock = mockStatic(AuthContext.class)) {
            authContextMock.when(AuthContext::getUsuarioAtual).thenReturn(usuarioAtual);
            when(usuarioRepository.isAdmin(usuarioAtual)).thenReturn(false);

            assertThatCode(() -> validator.validateView(tarefa))
                    .doesNotThrowAnyException();
        }
    }

    @Test
    public void validateView_quebra_quando_usuario_NAO_PertencerAoProjetoDaTarefa() {
        UUID usuarioAtual = UUID.randomUUID();

        Usuario outroUsuario = mock(Usuario.class);
        when(outroUsuario.getId()).thenReturn(UUID.randomUUID());

        Projeto projeto = mock(Projeto.class);
        when(projeto.getMembros()).thenReturn(Set.of(outroUsuario));

        Tarefa tarefa = mock(Tarefa.class);
        when(tarefa.getProjeto()).thenReturn(projeto);

        try (MockedStatic<AuthContext> authContextMock = mockStatic(AuthContext.class)) {
            authContextMock.when(AuthContext::getUsuarioAtual).thenReturn(usuarioAtual);
            when(usuarioRepository.isAdmin(usuarioAtual)).thenReturn(false);

            assertThatThrownBy(() -> validator.validateView(tarefa))
                    .isInstanceOf(UsuarioSemAcessoATarefaException.class);
        }
    }
}