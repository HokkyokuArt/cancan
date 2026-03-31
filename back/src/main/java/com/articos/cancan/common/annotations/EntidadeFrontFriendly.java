package com.articos.cancan.common.annotations;

import com.articos.cancan.utils.*;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntidadeFrontFriendly {
    String nome();

    Artigo artigo() default Artigo.FEMININO;
}
