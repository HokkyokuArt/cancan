package com.articos.cancan.domain.projeto;

import com.articos.cancan.domain.usuario.*;

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
}
