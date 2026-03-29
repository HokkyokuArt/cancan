package com.articos.cancan.domain.projeto;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, UUID> {
}
