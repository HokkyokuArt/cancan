package com.articos.cancan.domain.projeto;

import com.articos.cancan.common.crud.*;

import java.util.*;

import static com.articos.cancan.domain.projeto.ProjetoDataProvider.*;
import static org.mockito.Mockito.*;

class ProjetoServiceTest extends SuperCrudServiceTest<Projeto> {

    @Override
    protected SuperRepository<Projeto> mockRepository() {
        return mock(ProjetoRepository.class);
    }

    @Override
    protected SuperService<Projeto> createService(SuperRepository<Projeto> repository) {
        return new ProjetoService((ProjetoRepository) repository);
    }

    @Override
    protected Projeto buildEntity(UUID id) {
        return projetoCanCan(id, 10);
    }
}