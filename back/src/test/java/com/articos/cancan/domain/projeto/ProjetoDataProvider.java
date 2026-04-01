package com.articos.cancan.domain.projeto;

import com.articos.cancan.domain.usuario.*;
import com.articos.cancan.utils.*;

import java.util.*;

public class ProjetoDataProvider {
    public static Projeto projetoCanCan(Usuario dono, List<Usuario> membros) {
        Projeto projeto = new Projeto();
        projeto.setNome("Can can");
        projeto.setSigla("CAN");
        projeto.setDescricao("Seria muito legal colocar uma desc aqui mas eu to com preguiça");
        projeto.setDono(dono);
        projeto.getMembros().addAll(membros);
        return projeto;
    }

    public static Projeto projetoCanCan(UUID id, int version) {
        Usuario admin = UsuarioDataProvider.admin();
        Usuario member = UsuarioDataProvider.member();
        Projeto projeto = ProjetoDataProvider.projetoCanCan(admin, List.of(member));
        ReflectionUtils.setIn(projeto, "id", id);
        ReflectionUtils.setIn(projeto, "version", version);
        return projeto;
    }

    public static Projeto projetoCanCan() {
        return projetoCanCan(UUID.randomUUID(), 10);
    }
}
