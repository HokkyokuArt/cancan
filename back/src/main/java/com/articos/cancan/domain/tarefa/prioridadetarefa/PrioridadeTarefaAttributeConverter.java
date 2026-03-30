package com.articos.cancan.domain.tarefa.prioridadetarefa;

import com.articos.cancan.common.*;
import jakarta.persistence.*;

@Converter(autoApply = true)
public class PrioridadeTarefaAttributeConverter extends SuperAttributeConverter<PrioridadeTarefa> {
}
