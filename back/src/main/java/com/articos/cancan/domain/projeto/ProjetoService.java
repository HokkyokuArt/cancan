package com.articos.cancan.domain.projeto;

import com.articos.cancan.common.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class ProjetoService extends SuperService<Projeto> {
    public ProjetoService(JpaRepository<Projeto, UUID> repository) {
        super(repository);
    }
}
