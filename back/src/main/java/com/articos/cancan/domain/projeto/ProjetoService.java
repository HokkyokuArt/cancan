package com.articos.cancan.domain.projeto;

import com.articos.cancan.common.crud.*;
import org.springframework.stereotype.*;

@Service
public class ProjetoService extends SuperService<Projeto> {
    public ProjetoService(ProjetoRepository repository) {
        super(repository);
    }
}
