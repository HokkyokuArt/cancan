package com.articos.cancan.domain.usuario;

import com.articos.cancan.common.crud.*;

import java.util.*;

import static org.mockito.Mockito.mock;

class UsuarioServiceTest extends SuperCrudServiceTest<Usuario> {

    @Override
    protected SuperRepository<Usuario> mockRepository() {
        return mock(UsuarioRepository.class);
    }

    @Override
    protected SuperService<Usuario> createService(SuperRepository<Usuario> repository) {
        return new UsuarioService((UsuarioRepository) repository);
    }

    @Override
    protected Usuario buildEntity(UUID id) {
        return UsuarioDataProvider.admin(id, 10);
    }
}