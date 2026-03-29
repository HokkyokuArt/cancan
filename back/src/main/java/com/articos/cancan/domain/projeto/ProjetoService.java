package com.articos.cancan.domain.projeto;

import com.articos.cancan.common.*;
import org.springframework.stereotype.*;

@Service
public class ProjetoService extends SuperService<Projeto> {
    public ProjetoService(ProjetoRepository repository) {
        super(repository);
    }
}
