package com.articos.cancan.domain.tarefa.dto;

import com.articos.cancan.common.*;
import com.articos.cancan.domain.tarefa.*;
import lombok.*;

@NoArgsConstructor
@Getter
public class TarefaPayloadDTO extends SuperPayloadResponseDTO<Tarefa> {

    @Override
    public Tarefa toEntity() {
        return null;
    }
}
