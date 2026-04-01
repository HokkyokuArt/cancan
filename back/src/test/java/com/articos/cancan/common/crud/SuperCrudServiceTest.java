package com.articos.cancan.common.crud;

import com.articos.cancan.common.exceptions.core.*;
import org.junit.jupiter.api.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;


public abstract class SuperCrudServiceTest<T extends SuperEntity> {

    protected SuperRepository<T> repository;
    protected SuperService<T> service;

    protected SuperCrudServiceTest() {
        this.repository = mockRepository();
        this.service = createService(repository);
    }

    @Test
    public void list() {
        Specification<T> spec = (root, query, cb) -> cb.conjunction();
        Pageable pageable = PageRequest.of(0, 10);

        List<T> content = List.of(
                buildEntity(UUID.randomUUID()),
                buildEntity(UUID.randomUUID())
        );
        Page<T> page = new PageImpl<>(content, pageable, content.size());
        when(repository.findAll(spec, pageable)).thenReturn(page);

        Page<T> response = service.list(spec, pageable);
        assertThat(response).isNotNull();
        assertThat(response.getContent()).containsExactlyElementsOf(content);
        verify(repository).findAll(spec, pageable);
    }

    @Test
    public void findAll() {
        T entity1 = buildEntity(UUID.randomUUID());
        T entity2 = buildEntity(UUID.randomUUID());
        Set<UUID> ids = Set.of(entity1.getId(), entity2.getId());
        when(repository.findAllById(ids)).thenReturn(List.of(entity1, entity2));

        Map<UUID, T> response = service.findAll(ids);
        assertThat(response).hasSize(2);
        assertThat(response).containsEntry(entity1.getId(), entity1);
        assertThat(response).containsEntry(entity2.getId(), entity2);
    }

    @Test
    public void load() {
        UUID id = UUID.randomUUID();
        T entity = buildEntity(id);
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        T response = service.load(id);
        assertThat(response).isEqualTo(entity);
        verify(repository).findById(id);
    }

    @Test
    public void load_naoAchouValor() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());
        T response = service.load(id);
        assertThat(response).isNull();
        verify(repository).findById(id);
    }

    @Test
    public void loadWithException_sucesso() {
        UUID id = UUID.randomUUID();
        T entity = buildEntity(id);
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        T response = service.loadWithException(id, "buscar");
        assertThat(response).isEqualTo(entity);
        verify(repository).findById(id);
    }

    @Test
    public void loadWithException_erro() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.loadWithException(id, "buscar"))
                .isInstanceOf(EntidadeNaoEncontradaException.class);
        verify(repository).findById(id);
    }

    @Test
    public void save() {
        T entity = buildEntity(UUID.randomUUID());
        when(repository.save(entity)).thenReturn(entity);
        T response = service.save(entity);
        assertThat(response).isEqualTo(entity);
        verify(repository).save(entity);
    }

    @Test
    public void delete() {
        T entity = buildEntity(UUID.randomUUID());
        T response = service.delete(entity);
        assertThat(response).isEqualTo(entity);
        verify(repository).delete(entity);
    }

    protected abstract SuperRepository<T> mockRepository();

    protected abstract SuperService<T> createService(SuperRepository<T> repository);

    protected abstract T buildEntity(UUID id);
}