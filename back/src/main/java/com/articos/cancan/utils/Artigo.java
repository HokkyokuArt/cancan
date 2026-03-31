package com.articos.cancan.utils;

import lombok.*;

@RequiredArgsConstructor
public enum Artigo {
    MASCULINO("o"),
    FEMININO("a");

    private final String artigoChar;

    public String apply(String string) {
        return String.format(string, this.artigoChar);
    }
}
