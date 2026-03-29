package com.articos.cancan.common;

import org.springframework.data.jpa.domain.*;

public interface Filtro<T extends SuperEntity> {
    Specification<T> buildSpecification();
}
