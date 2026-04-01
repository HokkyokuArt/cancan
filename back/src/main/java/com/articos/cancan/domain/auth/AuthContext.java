package com.articos.cancan.domain.auth;

import org.springframework.security.core.*;
import org.springframework.security.core.context.*;

import java.util.*;

public class AuthContext {

    public static UUID getUsuarioAtual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return UUID.fromString(authentication.getName());
    }
}
