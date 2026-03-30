package com.articos.cancan.security.jwt.role;


import com.articos.cancan.common.interfaces.*;
import lombok.*;

@RequiredArgsConstructor
@Getter
public enum Role implements EnumAttributeConverterDescritivo {
    ROLE_ADMIN("A", "ADMIN"),
    ROLE_MEMBER("M", "MEMBER");

    private final String codigo;
    private final String descritivo;

    public boolean isAdmin() {
        return ROLE_ADMIN.equals(this);
    }

    public boolean isMember() {
        return ROLE_MEMBER.equals(this);
    }
}
