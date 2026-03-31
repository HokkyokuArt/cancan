package com.articos.cancan.domain.auth;

import org.springframework.security.core.*;
import org.springframework.security.core.context.*;

import java.util.*;

public class AuthContext {
    private static final Authentication instance = SecurityContextHolder.getContext().getAuthentication();

    public static UUID getUsuarioAtual() {
        return UUID.fromString(instance.getName());
    }
}
