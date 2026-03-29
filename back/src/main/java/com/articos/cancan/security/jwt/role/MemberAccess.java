package com.articos.cancan.security.jwt.role;

import org.springframework.security.access.prepost.*;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("isAuthenticated() and (hasRole('MEMBER') or hasRole('ADMIN'))")
public @interface MemberAccess {
}