package com.articos.cancan.common.exceptions.projeto;

import com.articos.cancan.common.exceptions.core.*;
import org.springframework.http.*;

public class DonoNaoPossuiRoleAdminException extends BusinessException {
    public DonoNaoPossuiRoleAdminException() {
        super("Dono do projeto deve ser admin",
                ProblemaCode.DONO_NAO_POSSUI_ROLE_ADMIN,
                HttpStatus.BAD_REQUEST,
                null);
    }
}
