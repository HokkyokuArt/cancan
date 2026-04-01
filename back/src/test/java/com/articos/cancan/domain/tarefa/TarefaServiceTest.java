package com.articos.cancan.domain.tarefa;

import com.articos.cancan.common.crud.*;

import java.util.*;

import static com.articos.cancan.domain.tarefa.TarefaDataProvider.*;
import static org.mockito.Mockito.*;

public class TarefaServiceTest extends SuperCrudServiceTest<Tarefa> {

    @Override
    protected SuperRepository<Tarefa> mockRepository() {
        return mock(TarefaRepository.class);
    }

    @Override
    protected SuperService<Tarefa> createService(SuperRepository<Tarefa> repository) {
        return new TarefaService((TarefaRepository) repository);
    }

    @Override
    protected Tarefa buildEntity(UUID id) {
        return tarefaSpikeCanCan(id, 10);
    }
}