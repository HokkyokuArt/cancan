package com.articos.cancan.common.interfaces;

import com.articos.cancan.common.crud.*;
import org.springframework.data.jpa.domain.*;

public interface Filtro<T extends SuperEntity> {
    Specification<T> buildSpecification();
}
