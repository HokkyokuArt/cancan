package com.articos.cancan.common.exceptions.core;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ProblemaDetalhe {
    private OffsetDateTime timestamp;
    private Integer status;
    private ProblemaCode code;
    private String error;
    private String message;
    private String path;
    private Map<String, Object> extra;
}
