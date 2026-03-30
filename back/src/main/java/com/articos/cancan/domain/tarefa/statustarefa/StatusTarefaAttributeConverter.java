package com.articos.cancan.domain.tarefa.statustarefa;

import com.articos.cancan.common.*;
import jakarta.persistence.*;

@Converter(autoApply = true)
public class StatusTarefaAttributeConverter extends SuperAttributeConverter<StatusTarefa> {
}
