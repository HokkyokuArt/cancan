package com.articos.cancan.common.crud;

import com.articos.cancan.common.*;
import com.articos.cancan.common.interfaces.*;
import com.articos.cancan.domain.usuario.*;
import com.articos.cancan.utils.ReflectionUtils;
import lombok.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.util.*;

import java.util.*;

import static com.articos.cancan.domain.usuario.UsuarioDataProvider.*;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public abstract class SuperCrudRestControllerIT<
        ENTIDADE extends SuperEntity<PAYLOAD_DTO, RESPONSE_DTO, LIST_RESPONSE_DTO>,
        PAYLOAD_DTO extends SuperPayloadDTO<ENTIDADE>,
        RESPONSE_DTO extends SuperResponseDTO<ENTIDADE>,
        FILTRO extends Filtro<ENTIDADE>,
        LIST_RESPONSE_DTO extends AbstractEntityDTO
        > extends SuperRestControllerIT {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public SuperRepository<ENTIDADE> repository;

    @Autowired
    public UsuarioRepository usuarioRepository;

    public static Usuario admin;
    public static Usuario member;

    @BeforeEach
    public void setupSuper() {
        admin = usuarioRepository.save(usuarioAdmin());
        member = usuarioRepository.save(usuarioMember());
    }

    @Test
    @SneakyThrows
    public void autocomplete() {
        persistValidEntity();
        flushAndClear();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "0");
        params.add("size", "10");

        ResultActions result = postRequest(url("/autocomplete"),
                getJwt(),
                objectMapper.writeValueAsString(buildValidFilter()),
                status().isOk(),
                params);

        result.andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.content[0].id").isNotEmpty())
                .andExpect(jsonPath("$.content[0].descritivo").isNotEmpty());
    }

    @Test
    @SneakyThrows
    public void pageable() {
        persistValidEntity();
        flushAndClear();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "0");
        params.add("size", "10");

        postRequest(url("/pageable"),
                getJwt(),
                objectMapper.writeValueAsString(buildValidFilter()),
                status().isOk(),
                params
        ).andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.content[*].id").isNotEmpty())
                .andExpect(jsonPath("$.content[*].descritivo").isNotEmpty());
    }

    @Test
    @SneakyThrows
    public void find() {
        ENTIDADE entity = persistValidEntity();
        flushAndClear();
        ResultActions result = getRequest(url("/find/" + entity.getId()),
                getJwt(),
                status().isOk()
        ).andExpect(jsonPath("$.id").value(entity.getId().toString()));
        assertFindResponse(result, entity);
    }

    @Test
    @SneakyThrows
    public void find_404() {
        getRequest(url("/find/" + UUID.randomUUID()),
                getJwt(),
                status().isNotFound()
        ).andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.path").value(containsString("/find/")));
    }

    @Test
    @SneakyThrows
    public void findAbstractEntity() {
        ENTIDADE entity = persistValidEntity();
        UUID id = entity.getId();
        flushAndClear();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("ids", id.toString());

        getRequest(url("/find-abstract-entity"),
                getJwt(),
                status().isOk(), params)
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].descritivo").isNotEmpty());
    }

    @Test
    @SneakyThrows
    public void findToEdit() {
        ENTIDADE entity = persistValidEntity();
        flushAndClear();
        ResultActions result = getRequest(url("/find-to-edit/" + entity.getId()),
                getJwt(),
                status().isOk()
        ).andExpect(jsonPath("$.id").value(entity.getId().toString()));
        assertFindToEditResponse(result, entity);
    }

    @Test
    @SneakyThrows
    public void findToEdit_404() {
        getRequest(url("/find-to-edit/" + UUID.randomUUID()),
                getJwt(),
                status().isNotFound()
        ).andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.path").value(containsString("/find-to-edit/")));
    }

    @Test
    @SneakyThrows
    public void create() {
        PAYLOAD_DTO sentDto = buildValidCreateDto();
        long countBefore = repository.count();

        ResultActions result =
                postRequest(url("/create"),
                        getJwt(),
                        objectMapper.writeValueAsString(sentDto),
                        status().isCreated()
                ).andExpect(jsonPath("$.id").isNotEmpty());
        assertResultResponse(result, sentDto);
        flushAndClear();

        long countAfter = repository.count();
        assertThat(countAfter).isEqualTo(countBefore + 1);

        ENTIDADE savedEntity = repository.findAll().stream().reduce((first, second) -> second).orElseThrow();
        assertThat(savedEntity.getId()).isNotNull();
        assertCreatedOrUpdatedEntity(savedEntity, sentDto);
    }

    @Test
    @SneakyThrows
    public void update() {
        ENTIDADE entity = persistValidEntity();
        UUID id = entity.getId();
        flushAndClear();

        long countBefore = repository.count();
        PAYLOAD_DTO sentDto = buildValidUpdateDto(entity);

        ResultActions result = putRequest(url("/update"),
                getJwt(),
                objectMapper.writeValueAsString(sentDto),
                status().isOk()
        ).andExpect(jsonPath("$.id").value(entity.getId().toString()));
        assertResultResponse(result, sentDto);
        flushAndClear();

        long countAfter = repository.count();
        assertThat(countAfter).isEqualTo(countBefore);

        ENTIDADE updatedEntity = repository.findById(id).orElseThrow();
        assertThat(updatedEntity.getId()).isEqualTo(entity.getId());
        assertCreatedOrUpdatedEntity(updatedEntity, sentDto);
    }

    @Test
    @SneakyThrows
    public void uptdate_404() {
        PAYLOAD_DTO dto = buildValidCreateDto();
        ReflectionUtils.setIn(dto, "id", UUID.randomUUID());
        putRequest(url("/update"),
                getJwt(),
                objectMapper.writeValueAsString(dto), status().isNotFound()
        ).andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.path").value(getBaseUrl() + "/update"));
    }

    @Test
    @SneakyThrows
    public void delete() {
        ENTIDADE entity = persistValidEntity();
        UUID id = entity.getId();
        flushAndClear();
        long countBefore = repository.count();
        ResultActions result = deleteRequest(url("/delete/" + id),
                getJwt(),
                status().isOk()
        ).andExpect(jsonPath("$.id").value(entity.getId().toString()));
        assertResultResponse(result, entity.toPayloadDTO());
        flushAndClear();
        long countAfter = repository.count();
        assertThat(countAfter).isEqualTo(countBefore - 1);
        assertThat(repository.findById(id)).isEmpty();
    }

    @Test
    @SneakyThrows
    public void delete_404() {
        deleteRequest(url("/delete/" + UUID.randomUUID()),
                getJwt(),
                status().isNotFound()
        ).andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.path").value(containsString("/delete/")));
    }

    public abstract void assertFindResponse(ResultActions result, ENTIDADE entity);

    public abstract void assertFindToEditResponse(ResultActions result, ENTIDADE entity);

    public abstract void assertResultResponse(ResultActions result, PAYLOAD_DTO sentDto);

    public abstract void assertCreatedOrUpdatedEntity(ENTIDADE savedEntity, PAYLOAD_DTO sentDto);

    public abstract ENTIDADE persistValidEntity();

    public abstract PAYLOAD_DTO buildValidCreateDto();

    public abstract PAYLOAD_DTO buildValidUpdateDto(ENTIDADE entity);

    public abstract FILTRO buildValidFilter();

    public abstract String getBaseUrl();

    public abstract RequestPostProcessor getJwt();

    private String url(String suffix) {
        return getBaseUrl() + suffix;
    }
}
