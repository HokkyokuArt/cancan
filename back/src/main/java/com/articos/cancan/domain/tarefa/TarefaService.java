package com.articos.cancan.domain.tarefa;

import com.articos.cancan.common.*;
import org.springframework.stereotype.*;

@Service
public class TarefaService extends SuperService<Tarefa> {

    public TarefaService(TarefaRepository repository) {
        super(repository);
    }
}
