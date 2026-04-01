package com.articos.cancan.common.crud;

import com.fasterxml.jackson.databind.*;
import jakarta.persistence.*;
import jakarta.transaction.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.security.core.authority.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.util.*;

import java.util.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public abstract class SuperRestControllerIT {
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private EntityManager entityManager;
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    protected static RequestPostProcessor jwtAdmin(UUID usuarioId) {
        return jwt()
                .jwt(jwt -> jwt.subject(usuarioId.toString()))
                .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    protected static RequestPostProcessor jwtMember(UUID usuarioId) {
        return jwt()
                .jwt(jwt -> jwt.subject(usuarioId.toString()))
                .authorities(new SimpleGrantedAuthority("ROLE_MEMBER"));
    }

    @SneakyThrows
    public ResultActions getRequest(
            String url,
            RequestPostProcessor jwt,
            ResultMatcher mockMvcResultMatchersStatusExpected,
            MultiValueMap<String, String> params
    ) {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(url)
                .with(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        if (params != null) {
            requestBuilder.params(params);
        }

        return mockMvc.perform(requestBuilder).andDo(result -> {
                    if (result.getResolvedException() != null) {
                        result.getResolvedException().printStackTrace();
                    }
                })
                .andExpect(mockMvcResultMatchersStatusExpected);
    }

    public ResultActions getRequest(String url, RequestPostProcessor jwt, ResultMatcher mockMvcResultMatchersStatusExpected) {
        return getRequest(url, jwt, mockMvcResultMatchersStatusExpected, null);
    }

    @SneakyThrows
    public ResultActions postRequest(
            String url,
            RequestPostProcessor jwt,
            String content,
            ResultMatcher mockMvcResultMatchersStatusExpected,
            MultiValueMap<String, String> params
    ) {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(url)
                .with(jwt)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        if (params != null) {
            requestBuilder.params(params);
        }
        if (content != null) {
            requestBuilder = requestBuilder.content(content);
        }

        return mockMvc.perform(requestBuilder).andDo(result -> {
                    if (result.getResolvedException() != null) {
                        result.getResolvedException().printStackTrace();
                    }
                })
                .andExpect(mockMvcResultMatchersStatusExpected);
    }

    public ResultActions postRequest(String url, RequestPostProcessor jwt, String content,
                                     ResultMatcher mockMvcResultMatchersStatusExpected) {
        return postRequest(url, jwt, content, mockMvcResultMatchersStatusExpected, null);
    }

    public ResultActions postRequest(String url, RequestPostProcessor jwt, ResultMatcher mockMvcResultMatchersStatusExpected, MultiValueMap<String,
            String> params) {
        return postRequest(url, jwt, null, mockMvcResultMatchersStatusExpected, params);
    }

    public ResultActions postRequest(String url, RequestPostProcessor jwt, ResultMatcher mockMvcResultMatchersStatusExpected) {
        return postRequest(url, jwt, null, mockMvcResultMatchersStatusExpected, null);
    }

    @SneakyThrows
    public ResultActions putRequest(
            String url,
            RequestPostProcessor jwt,
            String content,
            ResultMatcher mockMvcResultMatchersStatusExpected,
            MultiValueMap<String, String> params
    ) {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(url)
                .with(jwt)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        if (params != null) {
            requestBuilder.params(params);
        }
        if (content != null) {
            requestBuilder = requestBuilder.content(content);
        }

        return mockMvc.perform(requestBuilder).andDo(result -> {
            if (result.getResolvedException() != null) {
                result.getResolvedException().printStackTrace();
            }
        }).andExpect(mockMvcResultMatchersStatusExpected);
    }

    public ResultActions putRequest(String url, RequestPostProcessor jwt, String content,
                                    ResultMatcher mockMvcResultMatchersStatusExpected) {
        return putRequest(url, jwt, content, mockMvcResultMatchersStatusExpected, null);
    }

    public ResultActions putRequest(String url, RequestPostProcessor jwt, ResultMatcher mockMvcResultMatchersStatusExpected, MultiValueMap<String,
            String> params) {
        return putRequest(url, jwt, null, mockMvcResultMatchersStatusExpected, params);
    }

    public ResultActions putRequest(String url, RequestPostProcessor jwt, ResultMatcher mockMvcResultMatchersStatusExpected) {
        return putRequest(url, jwt, null, mockMvcResultMatchersStatusExpected, null);
    }

    @SneakyThrows
    public ResultActions deleteRequest(
            String url,
            RequestPostProcessor jwt,
            ResultMatcher mockMvcResultMatchersStatusExpected,
            MultiValueMap<String, String> params

    ) {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(url)
                .with(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        if (params != null) {
            requestBuilder.params(params);
        }
        return mockMvc.perform(requestBuilder)
                .andExpect(mockMvcResultMatchersStatusExpected);
    }

    public ResultActions deleteRequest(String url, RequestPostProcessor jwt, ResultMatcher mockMvcResultMatchersStatusExpected) {
        return deleteRequest(url, jwt, mockMvcResultMatchersStatusExpected, null);
    }
}
