package com.articos.cancan.domain.tarefa;

import com.articos.cancan.common.crud.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class TarefaService extends SuperService<Tarefa> {

    private TarefaRepository repository() {
        return (TarefaRepository) repository;
    }

    public TarefaService(TarefaRepository repository) {
        super(repository);
    }

    public Integer proximoNumero(UUID projetoId) {
        return repository().findProximoCodigoByProjeto(projetoId);
    }
}
