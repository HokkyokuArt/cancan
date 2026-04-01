package com.articos.cancan.domain.tarefa;

import com.articos.cancan.domain.projeto.*;
import com.articos.cancan.domain.tarefa.prioridadetarefa.*;
import com.articos.cancan.domain.tarefa.statustarefa.*;
import com.articos.cancan.domain.usuario.*;
import com.articos.cancan.utils.*;

import java.time.*;
import java.util.*;

import static com.articos.cancan.domain.tarefa.Tarefa.*;

public class TarefaDataProvider {
    public static Tarefa tarefaSpikeCanCan(Usuario responsavel, Projeto projeto, Integer proximoNumero) {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("SPIKE CANCAN");
        tarefa.setDescricao("Simplesmente o spike do melhor projeto já feito! (CONFIA)");
        tarefa.setResponsavel(responsavel);
        tarefa.setProjeto(projeto);
        tarefa.setPrioridade(PrioridadeTarefa.HIGH);
        tarefa.setStatus(StatusTarefa.TODO);
        tarefa.setPrazo(LocalDate.now().plusDays(5));
        tarefa.setSequencial(proximoNumero);
        tarefa.setCodigo(getCodigo(projeto.getSigla(), proximoNumero));
        return tarefa;
    }

    public static Tarefa tarefaSpikeCanCan(UUID id, int version) {
        Usuario admin = UsuarioDataProvider.admin();
        Usuario member = UsuarioDataProvider.member();
        Projeto projeto = ProjetoDataProvider.projetoCanCan(admin, List.of(member));
        Tarefa tarefa = TarefaDataProvider.tarefaSpikeCanCan(admin, projeto, 999);
        ReflectionUtils.setIn(tarefa, "id", id);
        ReflectionUtils.setIn(tarefa, "version", version);
        return tarefa;
    }
}
